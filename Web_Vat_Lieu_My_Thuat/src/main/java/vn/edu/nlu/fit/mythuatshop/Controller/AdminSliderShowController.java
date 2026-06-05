package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.SliderShow;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;
import vn.edu.nlu.fit.mythuatshop.Service.SliderShowService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import static java.lang.Math.max;


@WebServlet("/admin/sliders")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 20 * 1024 * 1024,
        maxRequestSize = 25 * 1024 * 1024
)
public class AdminSliderShowController extends HttpServlet {
    private final LogService logService = new LogService();
    private SliderShowService service;



    @Override
    public void init() {
        service = new SliderShowService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        int total = service.countByKeyword(null);
        int size = Math.max(total, 1);

        List<SliderShow> sliders = service.findPageByKeyword(null, 1, size);

        String editIdRaw = req.getParameter("editId");
        if (editIdRaw != null) {
            int editId = parseInt(editIdRaw, -1);
            if (editId > 0) {
                service.findById(editId).ifPresent(s -> req.setAttribute("editSlider", s));
            }
        }

        req.setAttribute("sliders", sliders);

        req.getRequestDispatcher("/admin/SliderShow.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        try {
            String action = req.getParameter("action");
            if (action == null){
                action = "";
            }
            switch (action) {
                case "create" -> {
                    if (!PermissionUtil.hasPermission(req, "SLIDER_CREATE")) {
                        PermissionUtil.showNoPermission(req, resp);
                        return;
                    }
                    handleCreate(req, resp);
                }
                case "update" ->{
                    if (!PermissionUtil.hasPermission(req, "SLIDER_UPDATE")) {
                        PermissionUtil.showNoPermission(req, resp);
                        return;
                    }
                    handleUpdate(req, resp);
                }
                case "toggle" -> {
                    if (!PermissionUtil.hasPermission(req, "SLIDER_LOCK")) {
                        PermissionUtil.showNoPermission(req, resp);
                        return;
                    }
                    handleToggle(req, resp);
                }
                case "delete" -> {
                    if (!PermissionUtil.hasPermission(req, "SLIDER_DELETE")) {
                        PermissionUtil.showNoPermission(req, resp);
                        return;
                    }
                    handleDelete(req, resp);
                }
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
        writeLog(req, "Tạo slider", "Quản lý Slider Show", null, s);
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
        writeLog(req, "Cập nhật slider", "Quản lý Slider Show", old, s);
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=updated");
    }

    private void handleToggle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseInt(req.getParameter("id"), -1);
        int currentStatus = parseInt(req.getParameter("currentStatus"), 0);
        if (id > 0) {
            Optional<SliderShow> oldOpt = service.findById(id);
            service.toggleStatus(id, currentStatus);
            Optional<SliderShow> newOpt = service.findById(id);

            if (oldOpt.isPresent() && newOpt.isPresent()) {
                writeLog(req, "Đổi trạng thái slider", "Quản lý Slider Show", oldOpt.get(), newOpt.get());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=toggled");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseInt(req.getParameter("id"), -1);
        if (id > 0) {
            Optional<SliderShow> oldOpt = service.findById(id);
            service.delete(id);

            if (oldOpt.isPresent()) {
                writeLog(req, "Xóa slider", "Quản lý Slider Show", oldOpt.get(), null);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/sliders?success=deleted");
    }

    private String resolveThumbnail(HttpServletRequest req, String oldThumbnail) throws Exception {
        Part filePart = req.getPart("thumbnailFile");

        if (filePart != null && filePart.getSize() > 0) {
            if(!isValidImage(filePart)) {
                throw new IllegalArgumentException("Ảnh không hợp lệ hoặc vượt quá 5MB");
            }
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

    private boolean isValidImage(Part part) {
        long maxSize = 5*1024*1024;
        if(part.getSize() > maxSize) {
            return false;
        }
        String fileName = part.getSubmittedFileName();
        if(fileName==null){
            return false;
        }
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".webp");
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

    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if (obj instanceof Users) {
            return ((Users) obj).getId();
        }
        return null;
    }

    private void writeLog(HttpServletRequest request, String label, String location, Object beforeData, Object afterData) {
        Integer userId = getCurrentUserId(request);
        if (userId != null) {
            logService.log(label, userId, location, beforeData, afterData);
        }
    }
}
