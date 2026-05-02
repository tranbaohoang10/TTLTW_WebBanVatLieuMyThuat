package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingInfo;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AdminOrderTrackingController", value = "/admin/order-tracking")
public class AdminOrderTrackingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();

        String idStr = request.getParameter("id");
        int orderId;

        try {
            orderId = Integer.parseInt(idStr);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
            return;
        }

        Order order = orderService.getOrderDetailForAdmin(orderId);
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/admin/orders");
            return;
        }

        request.setAttribute("order", order);

        if (order.getGhnOrderCode() == null || order.getGhnOrderCode().isBlank()) {
            request.setAttribute("trackingMessage", "Đơn hàng chưa được tạo vận đơn.");
            request.getRequestDispatcher("/admin/OrderTracking.jsp").forward(request, response);
            return;
        }

        GhnTrackingInfo tracking = orderService.getTrackingForAdmin(orderId);
        if (tracking == null) {
            request.setAttribute("trackingMessage", "Chưa lấy được thông tin vận đơn.");
        } else {
            request.setAttribute("tracking", tracking);
            request.setAttribute("trackingStatusText", mapTrackingStatus(tracking.getStatus()));
            request.setAttribute("trackingLeadtimeText", formatIsoTime(tracking.getLeadtime()));
        }

        request.getRequestDispatcher("/admin/OrderTracking.jsp").forward(request, response);
    }

    private String mapTrackingStatus(String status) {
        if (status == null) return "";

        switch (status) {
            case "ready_to_pick":
                return "Chờ lấy hàng";
            case "picking":
                return "Đang lấy hàng";
            case "picked":
                return "Đã lấy hàng";
            case "storing":
                return "Đang lưu kho";
            case "transporting":
                return "Đang trung chuyển";
            case "delivering":
                return "Đang giao hàng";
            case "delivered":
                return "Giao thành công";
            case "cancel":
                return "Đã hủy";
            default:
                return status;
        }
    }

    private String formatIsoTime(String text) {
        if (text == null || text.isBlank()) return "";
        try {
            OffsetDateTime odt = OffsetDateTime.parse(text);
            return odt.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"))
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            return text;
        }
    }
}