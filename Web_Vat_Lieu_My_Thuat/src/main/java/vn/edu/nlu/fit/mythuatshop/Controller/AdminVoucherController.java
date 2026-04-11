package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Dao.VoucherDao;
import vn.edu.nlu.fit.mythuatshop.Model.Voucher;
import vn.edu.nlu.fit.mythuatshop.Service.VoucherService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


@WebServlet(name = "AdminVoucherController", urlPatterns = {"/admin/vouchers"})
public class AdminVoucherController extends HttpServlet {

    private final VoucherService voucherService = new VoucherService();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final VoucherDao voucherDao = new VoucherDao();
    private static final int PAGE_SIZE = 10;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    static class VoucherRowDto {
        int id;
        String code;
        String name;
        String description;
        String startDate; // yyyy-MM-dd
        String endDate;   // yyyy-MM-dd
        double voucherCash;
        double minOrderValue;
        int quantity;
        int quantityUsed;
        int isActive;

        static VoucherRowDto from(Voucher v) {
            VoucherRowDto d = new VoucherRowDto();
            d.id = v.getId();
            d.code = v.getCode();
            d.name = v.getName();
            d.description = v.getDescription();
            d.voucherCash = v.getVoucherCash();
            d.minOrderValue = v.getMinOrderValue();
            d.quantity = v.getQuantity();
            d.quantityUsed = v.getQuantityUsed();
            d.isActive = v.getIsActive();
            d.startDate = (v.getStartDate() == null) ? "" : v.getStartDate().toLocalDate().toString();
            d.endDate   = (v.getEndDate() == null) ? "" : v.getEndDate().toLocalDate().toString();
            return d;
        }
    }

    static class AjaxResponse {
        List<VoucherRowDto> vouchers = new ArrayList<>();
        int currentPage;
        int totalPages;
        String keyword;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "edit" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                Voucher v = voucherService.getById(id);
                req.setAttribute("voucher", v);
                req.getRequestDispatcher("/admin/VoucherForm.jsp").forward(req, resp);
            }
            case "create" -> {
                req.getRequestDispatcher("/admin/VoucherForm.jsp").forward(req, resp);
            }
            default -> {
                // ====== TÌM KIẾM + PHÂN TRANG ======
                String keyword = req.getParameter("keyword");
                if (keyword == null) keyword = "";

                String pageParam = req.getParameter("page");
                int page = 1;
                try {
                    if (pageParam != null) {
                        page = Integer.parseInt(pageParam);
                        if (page < 1) page = 1;
                    }
                } catch (NumberFormatException ignored) {
                }

                int offset = (page - 1) * PAGE_SIZE;

                int totalRecords;
                List<Voucher> vouchers;
                if (!keyword.isBlank()) {
                    totalRecords = voucherDao.countByKeyword(keyword);
                    vouchers = voucherDao.searchPage(keyword, offset, PAGE_SIZE);
                } else {
                    totalRecords = voucherDao.countAll();
                    vouchers = voucherDao.findPage(offset, PAGE_SIZE);
                }

                int totalPages = (int) Math.ceil(totalRecords * 1.0 / PAGE_SIZE);
                if (totalPages == 0) totalPages = 1;
                if (page > totalPages) page = totalPages;
                boolean isAjax =
                        "1".equals(req.getParameter("ajax")) ||
                                "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"));

                if (isAjax) {
                    AjaxResponse out = new AjaxResponse();
                    for (Voucher v : vouchers) out.vouchers.add(VoucherRowDto.from(v));
                    out.currentPage = page;
                    out.totalPages = totalPages;
                    out.keyword = keyword;

                    resp.setContentType("application/json; charset=UTF-8");
                    resp.getWriter().write(gson.toJson(out));
                    return;
                }

                req.setAttribute("vouchers", vouchers);
                req.setAttribute("currentPage", page);
                req.setAttribute("totalPages", totalPages);
                req.setAttribute("keyword", keyword);

                req.getRequestDispatcher("/admin/Voucher.jsp").forward(req, resp);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> handleCreate(req);
                case "update" -> handleUpdate(req);
                case "delete" -> handleDelete(req);
                default -> { /* ignore */ }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/vouchers");
    }

    private void handleCreate(HttpServletRequest req) {
        Voucher v = buildVoucherFromRequest(req, false);
        voucherService.create(v);
    }

    private void handleUpdate(HttpServletRequest req) {
        Voucher v = buildVoucherFromRequest(req, true);
        voucherService.update(v);
    }

    private void handleDelete(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        voucherService.delete(id);
    }

    // đọc dữ liệu từ form (input name=...)
    private Voucher buildVoucherFromRequest(HttpServletRequest req, boolean hasId) {
        Voucher v = new Voucher();
        if (hasId) {
            v.setId(Integer.parseInt(req.getParameter("id")));
        }

        v.setCode(req.getParameter("code"));
        v.setName(req.getParameter("name"));
        v.setDescription(req.getParameter("description"));
        v.setVoucherType(req.getParameter("voucherType"));
        v.setVoucherCash(Double.parseDouble(req.getParameter("voucherCash")));
        v.setVoucherPercent(Double.parseDouble(req.getParameter("voucherPercent")));
        v.setMaxDiscount(Double.parseDouble(req.getParameter("maxDiscount")));
        v.setMinOrderValue(Double.parseDouble(req.getParameter("minOrderValue")));
        v.setQuantity(Integer.parseInt(req.getParameter("quantity")));
        v.setQuantityUsed(Integer.parseInt(req.getParameter("quantityUsed")));
        v.setIsActive(Integer.parseInt(req.getParameter("isActive")));

        String start = req.getParameter("startDate");
        String end = req.getParameter("endDate");
        if (start != null && !start.isEmpty()) {
            v.setStartDate(LocalDate.parse(start, dtf).atStartOfDay());
        }
        if (end != null && !end.isEmpty()) {
            v.setEndDate(LocalDate.parse(end, dtf).atStartOfDay());
        }

        return v;
    }
}
