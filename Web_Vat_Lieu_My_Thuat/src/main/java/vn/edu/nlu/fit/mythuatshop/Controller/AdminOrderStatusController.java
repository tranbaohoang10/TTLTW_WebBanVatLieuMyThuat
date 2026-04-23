package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;

@WebServlet(name = "AdminOrderStatusController", urlPatterns = {"/admin/orders/status"})
public class AdminOrderStatusController extends HttpServlet {
    private OrderService orderService = new OrderService();
    private final LogService logService = new LogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        boolean success = false;
        String message = "Cập nhật trạng thái thất bại";
        String newStatus = "";

        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            newStatus = req.getParameter("statusName");

            String beforeStatus = "";
            Order oldOrder = orderService.getOrderDetailForAdmin(orderId);
            if (oldOrder != null && oldOrder.getStatusName() != null) {
                beforeStatus = oldOrder.getStatusName();
            }

            success = orderService.adminUpdateOrderStatus(orderId, newStatus);

            if (success) {
                Integer userId = getCurrentUserId(req);
                if (userId != null) {
                    logService.log(
                            "Cập nhật trạng thái đơn hàng",
                            userId,
                            "AdminOrderStatusController#updateStatus",
                            beforeStatus,
                            newStatus
                    );
                }
                message = "Cập nhật trạng thái thành công";
            } else {
                message = "Không thể chuyển trạng thái đơn hàng";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "Có lỗi xảy ra khi cập nhật trạng thái";
        }

        String json = "{"
                + "\"success\":" + success + ","
                + "\"message\":\"" + message + "\","
                + "\"newStatus\":\"" + newStatus + "\""
                + "}";

        resp.getWriter().write(json);
    }

    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if (obj instanceof Users) {
            return ((Users) obj).getId();
        }
        return null;
    }
}