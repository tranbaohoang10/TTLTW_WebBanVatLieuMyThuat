package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.edu.nlu.fit.mythuatshop.Model.Category;
import vn.edu.nlu.fit.mythuatshop.Service.CategoryService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet(name = "AdminCategoryController", value = "/admin/categories")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminCategoryController extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("categories", categoryService.getAllcategories());
        req.getRequestDispatcher("/admin/Category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> handleCreate(req);
                case "update" -> handleUpdate(req);
                case "toggleActive" -> handleToggleActive(req);
                default -> {}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private void handleCreate(HttpServletRequest req) throws Exception {
        String name = req.getParameter("categoryName");

        Part thumbPart = req.getPart("thumbnail");
        String thumbUrl = saveUploadAndReturnUrl(req, thumbPart, "uploads/categories");

        Category c = new Category();
        c.setCategoryName(name);
        c.setThumbnail(thumbUrl);
        c.setIsActive(1);

        categoryService.create(c);
    }

    private void handleUpdate(HttpServletRequest req) throws Exception {
        int id = parseInt(req.getParameter("id"), 0);
        if (id <= 0) return;

        String name = req.getParameter("categoryName");

        Category old = categoryService.getCategoryById(id);
        String oldThumb = (old != null) ? old.getThumbnail() : null;

        Part thumbPart = req.getPart("thumbnail");
        String newThumb = null;
        if (thumbPart != null && thumbPart.getSize() > 0) {
            newThumb = saveUploadAndReturnUrl(req, thumbPart, "uploads/categories");
        }

        Category c = new Category();
        c.setId(id);
        c.setCategoryName(name);
        c.setThumbnail(newThumb != null ? newThumb : oldThumb);

        categoryService.update(c);
    }

    private void handleToggleActive(HttpServletRequest req) {
        int id = parseInt(req.getParameter("id"), 0);
        int current = parseInt(req.getParameter("isActive"), 1);
        if (id <= 0) return;

        categoryService.toggleActive(id, current);
    }

    // ============ helpers ============
    private String saveUploadAndReturnUrl(HttpServletRequest req, Part part, String folder) throws IOException {
        if (part == null || part.getSize() == 0) return null;

        String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);

        String savedName = UUID.randomUUID() + ext;

        String realPath = req.getServletContext().getRealPath("/");
        File uploadDir = new File(realPath, folder);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File savedFile = new File(uploadDir, savedName);
        part.write(savedFile.getAbsolutePath());

        return "/" + folder + "/" + savedName;
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
