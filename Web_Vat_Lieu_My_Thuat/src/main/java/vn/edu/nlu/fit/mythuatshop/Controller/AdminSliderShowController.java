package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.SliderShow;
import vn.edu.nlu.fit.mythuatshop.Service.SliderShowService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@WebServlet("/admin/sliders")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 20 * 1024 * 1024
)
public class AdminSliderShowController extends HttpServlet {

    private SliderShowService service;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();



    @Override
    public void init() {
        service = new SliderShowService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String q = trimOrNull(req.getParameter("q"));
        int page = parseInt(req.getParameter("page"), 1);
        int size = parseInt(req.getParameter("size"), 10);
        if (size < 5) size = 5;
        if (size > 50) size = 50;

        int total = service.countByKeyword(q);
        int totalPages = (int) Math.ceil(total * 1.0 / size);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;


        List<SliderShow> sliders = service.findPageByKeyword(q, page, size);

        boolean isAjax =
                "1".equals(req.getParameter("ajax")) ||
                        "XMLHttpRequest".equalsIgnoreCase(req.getHeader("X-Requested-With"));

        if (isAjax) {
            Map<String, Object> out = new HashMap<>();
            out.put("sliders", sliders);
            out.put("currentPage", page);
            out.put("totalPages", totalPages);
            out.put("q", q == null ? "" : q);
            out.put("size", size);

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(gson.toJson(out));
            return;
        }


        String editIdRaw = req.getParameter("editId");
        if (editIdRaw != null) {
            int editId = parseInt(editIdRaw, -1);
            if (editId > 0) {
                service.findById(editId).ifPresent(s -> req.setAttribute("editSlider", s));
            }
        }

        req.setAttribute("sliders", sliders);
        req.setAttribute("q", q == null ? "" : q);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("total", total);

        req.getRequestDispatcher("/admin/SliderShow.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> handleCreate(req, resp);
                case "update" -> handleUpdate(req, resp);
                case "toggle" -> handleToggle(req, resp);
                case "delete" -> handleDelete(req, resp);
                default -> resp.sendRedirect(req.getContextPath() + "/admin/sliders");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            doGet(req, resp);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String title = trimOrNull(req.getParameter("title"));
        String linkTo = trimOrNull(req.getParameter("linkTo"));
        int status = parseInt(req.getParameter("status"), 1);
        int indexOrder = parseInt(req.getParameter("indexOrder"), 1);

        String thumbnail = resolveThumbnail(req, null);

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }
        if (indexOrder <= 0) {
            throw new IllegalArgumentException("indexOrder phải > 0");
        }
        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new IllegalArgumentException("Bạn cần upload ảnh hoặc nhập URL thumbnail");
        }
        if (service.existsIndexOrder(indexOrder, null)) {
            throw new IllegalArgumentException("indexOrder đã tồn tại, hãy chọn số khác");
        }

        SliderShow s = new SliderShow();
        s.setTitle(title);
        s.setLinkTo(linkTo);
        s.setStatus(status == 1 ? 1 : 0);
        s.setIndexOrder(indexOrder);
        s.setThumbnail(thumbnail);

        service.create(s);
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=created");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = parseInt(req.getParameter("id"), -1);
        if (id <= 0) throw new IllegalArgumentException("ID không hợp lệ");

        Optional<SliderShow> oldOpt = service.findById(id);
        if (oldOpt.isEmpty()) {
            throw new IllegalArgumentException("Slider không tồn tại");
        }

        SliderShow old = oldOpt.get();

        String title = trimOrNull(req.getParameter("title"));
        String linkTo = trimOrNull(req.getParameter("linkTo"));
        int status = parseInt(req.getParameter("status"), old.getStatus());
        int indexOrder = parseInt(req.getParameter("indexOrder"), old.getIndexOrder());

        String thumbnail = resolveThumbnail(req, old.getThumbnail());

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }
        if (indexOrder <= 0) {
            throw new IllegalArgumentException("indexOrder phải > 0");
        }
        if (thumbnail == null || thumbnail.isBlank()) {
            throw new IllegalArgumentException("Thumbnail không hợp lệ");
        }
        if (service.existsIndexOrder(indexOrder, id)) {
            throw new IllegalArgumentException("indexOrder đã tồn tại, hãy chọn số khác");
        }

        SliderShow s = new SliderShow();
        s.setId(id);
        s.setTitle(title);
        s.setLinkTo(linkTo);
        s.setStatus(status == 1 ? 1 : 0);
        s.setIndexOrder(indexOrder);
        s.setThumbnail(thumbnail);

        service.update(s);
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=updated");
    }

    private void handleToggle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseInt(req.getParameter("id"), -1);
        int currentStatus = parseInt(req.getParameter("currentStatus"), 0);
        if (id > 0) service.toggleStatus(id, currentStatus);
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=toggled");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseInt(req.getParameter("id"), -1);
        if (id > 0) service.delete(id);
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=deleted");
    }

    private String resolveThumbnail(HttpServletRequest req, String oldThumbnail) throws Exception {
        Part filePart = req.getPart("thumbnailFile"); // servlet phải có @MultipartConfig

        if (filePart != null && filePart.getSize() > 0) {
            String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int dot = submitted.lastIndexOf('.');
            if (dot >= 0) ext = submitted.substring(dot);

            String fileName = UUID.randomUUID() + ext;

            String root = req.getServletContext().getRealPath("/");
            File dir = new File(root, "uploads/sliders");
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(dir, fileName);
            filePart.write(dest.getAbsolutePath());

            return req.getContextPath() + "/uploads/sliders/" + fileName;
        }

        String thumbnailUrl = trimOrNull(req.getParameter("thumbnailUrl"));
        if (thumbnailUrl != null && !thumbnailUrl.isBlank()) {
            return thumbnailUrl;
        }
        return oldThumbnail;
    }


    private String trimOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }


}
