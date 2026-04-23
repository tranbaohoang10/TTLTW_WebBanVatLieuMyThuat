package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Dao.VoucherDao;
import vn.edu.nlu.fit.mythuatshop.Model.Voucher;
import vn.edu.nlu.fit.mythuatshop.Service.VoucherService;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "AdminVoucherController", urlPatterns = {"/admin/vouchers"})
public class AdminVoucherController extends HttpServlet {

    private final VoucherService voucherService = new VoucherService();
    private final VoucherDao voucherDao = new VoucherDao();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LogService logService = new LogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        } else {
            showVoucherList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            if ("create".equals(action)) {
                createVoucher(request);
            } else if ("update".equals(action)) {
                updateVoucher(request);
            } else if ("delete".equals(action)) {
                deleteVoucher(request);
            }else if ("lock".equals(action)) {
                lockVoucher(request);
            }else if ("unlock".equals(action)) {
                unlockVoucher(request);
            }

            response.sendRedirect(request.getContextPath() + "/admin/vouchers");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/VoucherForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher voucher = voucherService.getById(id);
        request.setAttribute("voucher", voucher);
        request.getRequestDispatcher("/admin/VoucherForm.jsp").forward(request, response);
    }

    private void showVoucherList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Voucher> vouchers = voucherDao.findAll();
        request.setAttribute("vouchers", vouchers);
        request.getRequestDispatcher("/admin/Voucher.jsp").forward(request, response);
    }

    private void createVoucher(HttpServletRequest request) {
        Voucher voucher = getVoucherFromRequest(request, false);
        boolean ok =  voucherService.create(voucher);
        if(ok){
            writeLog(request, "Tạo voucher", "AdminVoucherController#create", null, voucher);
        }
    }

    private void updateVoucher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher before  = voucherService.getById(id);
        Voucher voucher = getVoucherFromRequest(request, true);
        boolean ok = voucherService.update(voucher);
        if(ok){
            Voucher after  = voucherService.getById(id);
            writeLog(request, "Cập nhật voucher", "AdminVoucherController#update", before, after);
        }
    }

    private void deleteVoucher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher before  = voucherService.getById(id);
        boolean ok = voucherService.delete(id);
        if(ok){
            writeLog(request, "Xóa voucher",  "AdminVoucherController#delete", before, null);
        }
    }
    private void lockVoucher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher before = voucherService.getById(id);
        boolean ok = voucherService.lock(id);
        if(ok){
            Voucher after  = voucherService.getById(id);
            writeLog(request, "Khóa voucher",  "AdminVoucherController#lock", before, after);
        }
    }
    private void unlockVoucher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher before = voucherService.getById(id);
        boolean ok = voucherService.unlock(id);
        if(ok){
            Voucher after = voucherService.getById(id);
            writeLog(request, "Mở khóa voucher", "AdminVoucherController#unlock", before, after);
        }
        }
    private Voucher getVoucherFromRequest(HttpServletRequest request, boolean isUpdate) {
        Voucher voucher = new Voucher();

        if (isUpdate) {
            voucher.setId(Integer.parseInt(request.getParameter("id")));
        }

        voucher.setCode(request.getParameter("code"));
        voucher.setName(request.getParameter("name"));
        voucher.setDescription(request.getParameter("description"));
        voucher.setVoucherType(request.getParameter("voucherType"));
        voucher.setVoucherCash(parseDoubleOrDefault(request.getParameter("voucherCash"), 0));
        voucher.setVoucherPercent(parseDoubleOrDefault(request.getParameter("voucherPercent"), 0));
        voucher.setMaxDiscount(parseDoubleOrDefault(request.getParameter("maxDiscount"), 0));
        voucher.setMinOrderValue(parseDoubleOrDefault(request.getParameter("minOrderValue"), 0));
        voucher.setQuantity(parseIntOrDefault(request.getParameter("quantity"), 0));
        voucher.setQuantityUsed(parseIntOrDefault(request.getParameter("quantityUsed"), 0));
        voucher.setIsActive(parseIntOrDefault(request.getParameter("isActive"), 1));

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        if (startDate != null && !startDate.isEmpty()) {
            voucher.setStartDate(LocalDate.parse(startDate, dtf).atStartOfDay());
        }

        if (endDate != null && !endDate.isEmpty()) {
            voucher.setEndDate(LocalDate.parse(endDate, dtf).atStartOfDay());
        }


        if (voucher.getStartDate() != null && voucher.getEndDate() != null
                && voucher.getEndDate().isBefore(voucher.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu");
        }
        return voucher;
    }
    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Double.parseDouble(value.trim());
    }
    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if(obj instanceof Users){
            return ((Users)obj).getId();
        }
        return null;
    }
    private void writeLog(HttpServletRequest request, String label, String location, Object beforeData, Object afterData) {
        Users user = (Users) request.getSession().getAttribute("currentUser");
        if(user != null){
            logService.log(label, user.getId(), location, beforeData, afterData);
        }
    }
}