package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Supplier;
import vn.edu.nlu.fit.mythuatshop.Service.SupplierService;

import java.io.IOException;

@WebServlet(name = "AdminSupplierController", value = "/admin/suppliers")
public class AdminSupplierController extends HttpServlet {
    private SupplierService supplierService;

    @Override
    public void init() {
        supplierService = new SupplierService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("suppliers", supplierService.getAllSuppliers());
        req.getRequestDispatcher("/admin/Supplier.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create" -> handleCreate(req);
                case "update" -> handleUpdate(req);
                case "toggleStatus" -> handleToggleStatus(req);
                default -> req.getSession().setAttribute("supplierError", "Thao tác không hợp lệ.");
            }
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("supplierError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("supplierError", "Có lỗi xảy ra khi xử lý nhà cung cấp.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/suppliers");
    }

    private void handleCreate(HttpServletRequest req) {
        Supplier supplier = buildSupplierFromRequest(req);

        boolean ok = supplierService.create(supplier);

        if (ok) {
            req.getSession().setAttribute("supplierMessage", "Thêm nhà cung cấp thành công.");
        } else {
            req.getSession().setAttribute("supplierError", "Không thể thêm nhà cung cấp.");
        }
    }

    private void handleUpdate(HttpServletRequest req) {
        Supplier supplier = buildSupplierFromRequest(req);
        supplier.setId(parseInt(req.getParameter("id"), 0));

        boolean ok = supplierService.update(supplier);

        if (ok) {
            req.getSession().setAttribute("supplierMessage", "Cập nhật nhà cung cấp thành công.");
        } else {
            req.getSession().setAttribute("supplierError", "Không thể cập nhật nhà cung cấp.");
        }
    }

    private void handleToggleStatus(HttpServletRequest req) {
        int id = parseInt(req.getParameter("id"), 0);
        int currentIsActive = parseInt(req.getParameter("isActive"), 1);

        boolean ok = supplierService.toggleStatus(id, currentIsActive);

        if (ok) {
            req.getSession().setAttribute("supplierMessage", "Cập nhật trạng thái nhà cung cấp thành công.");
        } else {
            req.getSession().setAttribute("supplierError", "Không thể cập nhật trạng thái nhà cung cấp.");
        }
    }

    private Supplier buildSupplierFromRequest(HttpServletRequest req) {
        Supplier supplier = new Supplier();

        supplier.setSupplierCode(req.getParameter("supplierCode"));
        supplier.setName(req.getParameter("name"));
        supplier.setPhone(req.getParameter("phone"));
        supplier.setEmail(req.getParameter("email"));
        supplier.setAddress(req.getParameter("address"));
        supplier.setNote(req.getParameter("note"));

        return supplier;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}