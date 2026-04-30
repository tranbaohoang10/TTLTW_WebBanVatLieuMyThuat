package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;
import vn.edu.nlu.fit.mythuatshop.Service.VnpayService;

import java.io.IOException;

@WebServlet(name = "VnpayReturnController", value = "/vnpay-return")
public class VnpayReturnController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VnpayService vnpayService = new VnpayService();
        OrderService orderService = new OrderService();
        boolean ok = vnpayService.verifyReturn(req);


        if (!ok) {
            req.setAttribute("errorMessage", "Dữ liệu VNPAY trả về không hợp lệ .");
            req.getRequestDispatcher("/InfoPayment.jsp").forward(req, resp);
            return;
        }

        // 2) lấy dữ liệu
        String responseCode = req.getParameter("vnp_ResponseCode");
        String tranStatus = req.getParameter("vnp_TransactionStatus");
        String txnRef = req.getParameter("vnp_TxnRef"); // orderId
        String amountStr = req.getParameter("vnp_Amount");

        int orderId = Integer.parseInt(txnRef.split("_")[0]);
        long amountVnd = Long.parseLong(amountStr) / 100;
        boolean success = "00".equals(responseCode) && "00".equals(tranStatus);

        if (!success) {

            orderService.markVnpayFailed(orderId);
            req.setAttribute("errorMessage", "Thanh toán VNPAY thất bại hoặc bị hủy.");
            req.getRequestDispatcher("/InfoPayment.jsp").forward(req, resp);
            return;
        }

        Order paidOrder = null;
        try {
            paidOrder = orderService.confirmVnpayPaid(orderId, amountVnd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (paidOrder == null) {
            req.setAttribute("errorMessage",
                    "Không thể xác nhận thanh toán (có thể đơn không tồn tại / sai tiền / thiếu tồn kho).");
            req.getRequestDispatcher("/InfoPayment.jsp").forward(req, resp);
            return;
        }


        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        Cart cartTemp = (Cart) session.getAttribute("cartTemp");

        if (cart != null && cartTemp != null) {
            cart.removeCartTemp(cartTemp);
            session.setAttribute("cart", cart);
            session.setAttribute("cartCount", cart.getTotalQuantity());
        }

        session.removeAttribute("cartTemp");
        session.setAttribute("paidOrder", paidOrder);

        resp.sendRedirect(req.getContextPath() + "/payment-success");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}