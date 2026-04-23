package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;

@WebServlet(name = "AdminOrderStatusController", urlPatterns = {"/admin/orders/status"})
public class AdminOrderStatusController extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        boolean success = false;
        String message = "Cập nhật trạng thái thất bại";
        String newStatus = "";

        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            newStatus = req.getParameter("statusName");

            success = orderService.adminUpdateOrderStatus(orderId, newStatus);

            if (success) {
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

}