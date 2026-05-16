package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.InventoryService;

import java.io.IOException;

@WebServlet(name = "AdminInventoryController", value = "/admin/inventory")
public class AdminInventoryController extends HttpServlet {
    private InventoryService inventoryService;

    @Override
    public void init() {
        inventoryService = new InventoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("products", inventoryService.getInventoryProducts());
        request.setAttribute("history", inventoryService.getHistory());

        request.getRequestDispatcher("/admin/Inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        int productId = parseInt(request.getParameter("productId"), -1);
        String note = request.getParameter("note");
        Integer adminId = getCurrentUserId(request);

        boolean ok = false;

        if ("importStock".equals(action)) {
            int quantity = parseInt(request.getParameter("quantity"), 0);
            ok = inventoryService.importStock(productId, quantity, note, adminId);
        } else if ("adjustStock".equals(action)) {
            int newStock = parseInt(request.getParameter("newStock"), -1);
            ok = inventoryService.adjustStock(productId, newStock, note, adminId);
        }

        if (ok) {
            request.getSession().setAttribute("inventoryMessage", "Cập nhật tồn kho thành công.");
        } else {
            request.getSession().setAttribute("inventoryError", "Cập nhật tồn kho thất bại.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/inventory");
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if (obj instanceof Users) {
            return ((Users) obj).getId();
        }
        return null;
    }
}