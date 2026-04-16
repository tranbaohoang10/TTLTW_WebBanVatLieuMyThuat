package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;

@WebServlet(name = "CancelOrderController", value = "/cancel-order")
public class CancelOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/order-history?status=all");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String idRaw = request.getParameter("orderId");
        String cancelReason = request.getParameter("cancelReason");

        int orderId;
        try {
            orderId = Integer.parseInt(idRaw);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()
                    + "/order-history?status=all&msg=cancel_fail");
            return;
        }


        if (cancelReason == null || cancelReason.isBlank()) {
            cancelReason = "Khách hàng không muốn mua nữa";
        }

        boolean ok = orderService.cancelOrder(currentUser.getId(), orderId, cancelReason);

        response.sendRedirect(request.getContextPath()
                + "/order-history?status=all&msg=" + (ok ? "cancel_ok" : "cancel_fail"));
    }
}