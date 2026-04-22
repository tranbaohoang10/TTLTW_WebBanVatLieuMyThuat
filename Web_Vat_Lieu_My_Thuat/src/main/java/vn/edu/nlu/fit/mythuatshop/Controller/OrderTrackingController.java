package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingInfo;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;

@WebServlet(name = "OrderTrackingController", value = "/order-tracking")
public class OrderTrackingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");
        int orderId;
        try {
            orderId = Integer.parseInt(idStr);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/order-history?status=all");
            return;
        }

        Order order = orderService.getOrderDetail(currentUser.getId(), orderId);
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/order-history?status=all");
            return;
        }

        request.setAttribute("order", order);

        if (order.getGhnOrderCode() == null || order.getGhnOrderCode().isBlank()) {
            request.setAttribute("trackingMessage", "Đơn hàng chưa được tạo vận đơn.");
            request.getRequestDispatcher("/OrderTracking.jsp").forward(request, response);
            return;
        }

        GhnTrackingInfo tracking = orderService.getTrackingForUser(currentUser.getId(), orderId);
        if (tracking == null) {
            request.setAttribute("trackingMessage", "Chưa lấy được thông tin vận đơn.");
        } else {
            request.setAttribute("tracking", tracking);
        }

        request.getRequestDispatcher("/OrderTracking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}