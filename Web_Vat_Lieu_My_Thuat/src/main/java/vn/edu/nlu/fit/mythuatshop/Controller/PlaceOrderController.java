package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;
import vn.edu.nlu.fit.mythuatshop.Service.VnpayService;

import java.io.IOException;

@WebServlet(name = "PlaceOrderController", value = "/place-order")
public class PlaceOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        VnpayService vnpayService = new VnpayService();

        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }
        Cart cartTemp = (Cart) session.getAttribute("cartTemp");
        if (cartTemp == null || cartTemp.cartSize() == 0) {
            resp.sendRedirect("Cart.jsp");
            return;
        }
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");
        String paymentName = req.getParameter("payment");
        Integer voucherId = (Integer) session.getAttribute("appliedVoucherId");

        Order order = orderService.createOrder(currentUser, cartTemp, fullName, email, phone,
                address, note, paymentName, voucherId);

        if (order == null) {
            req.setAttribute("errorMessage", "Đặt hàng thất bại");
            req.getRequestDispatcher("/InfoPayment.jsp").forward(req, resp);
            return;
        }

        if ("VNPAY".equalsIgnoreCase(paymentName)) {
            String paymentUrl = vnpayService.buildPaymentUrl(req, order);
            resp.sendRedirect(paymentUrl);
            return; 
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeCartTemp(cartTemp);
            session.setAttribute("cart", cart);
            session.setAttribute("cartCount", cart.getTotalQuantity());
        }

        session.removeAttribute("cartTemp");
        session.setAttribute("paidOrder", order);
        session.removeAttribute("appliedVoucherId");
        resp.sendRedirect(req.getContextPath() + "/payment-success");
    }

}