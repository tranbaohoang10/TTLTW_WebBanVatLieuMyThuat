package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.InventoryExcelReportService;
import vn.edu.nlu.fit.mythuatshop.Service.InventoryService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "AdminInventoryController", value = "/admin/inventory")
public class AdminInventoryController extends HttpServlet {
    private InventoryService inventoryService;
    private InventoryExcelReportService inventoryExcelReportService;

    @Override
    public void init() {
        inventoryService = new InventoryService();
        inventoryExcelReportService = new InventoryExcelReportService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if ("downloadExcelReport".equals(action)) {
            if (!PermissionUtil.hasPermission(request, "INVENTORY_VIEW")) {
                PermissionUtil.showNoPermission(request, response);
                return;
            }
            if (!inventoryExcelReportService.reportFileExists()) {
                request.getSession().setAttribute("inventoryError", "Chưa có file báo cáo Excel. Vui lòng cập nhật báo cáo trước.");
                response.sendRedirect(request.getContextPath() + "/admin/inventory");
                return;
            }
            Path reportPath = inventoryExcelReportService.getReportPath();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"bao-cao-ton-kho.xlsx\"");

            Files.copy(reportPath, response.getOutputStream());
            return;
        }
        request.setAttribute("lowStockThreshold", inventoryService.getLowStockThreshold());
        request.setAttribute("lowStockCount", inventoryService.countLowStockProducts());
        request.setAttribute("outOfStockCount", inventoryService.countOutOfStockProducts());
        request.setAttribute("lowStockProducts", inventoryService.getLowStockProducts());
        request.setAttribute("outOfStockProducts", inventoryService.getOutOfStockProducts());
        request.setAttribute("products", inventoryService.getInventoryProducts());
        request.setAttribute("history", inventoryService.getHistory());

        request.getRequestDispatcher("/admin/Inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException,ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("updateExcelReport".equals(action)) {
            if (!PermissionUtil.hasPermission(request, "INVENTORY_VIEW")) {
                PermissionUtil.showNoPermission(request, response);
                return;
            }

            try {
                inventoryExcelReportService.updateTodayReport();
                request.getSession().setAttribute("inventoryMessage", "Cập nhật báo cáo Excel hôm nay thành công.");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("inventoryError", "Cập nhật báo cáo Excel thất bại.");
            }

            response.sendRedirect(request.getContextPath() + "/admin/inventory");
            return;
        }
        int productId = parseInt(request.getParameter("productId"), -1);
        String note = request.getParameter("note");
        Integer adminId = getCurrentUserId(request);

        boolean ok = false;

        if ("importStock".equals(action)) {
            if (!PermissionUtil.hasPermission(request, "INVENTORY_IMPORT")) {
                PermissionUtil.showNoPermission(request, response);
                return;
            }
            int quantity = parseInt(request.getParameter("quantity"), 0);
            ok = inventoryService.importStock(productId, quantity, note, adminId);
        } else if ("adjustStock".equals(action)) {
            if (!PermissionUtil.hasPermission(request, "INVENTORY_ADJUST")) {
                PermissionUtil.showNoPermission(request, response);
                return;
            }
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