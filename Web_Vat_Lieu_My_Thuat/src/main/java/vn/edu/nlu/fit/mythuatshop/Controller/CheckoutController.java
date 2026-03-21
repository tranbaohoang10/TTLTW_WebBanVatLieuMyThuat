package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.io.IOException;

@WebServlet(name = "CheckoutController", value = "/checkout")
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.cartSize() == 0) {
            resp.sendRedirect("Cart.jsp");
            return;
        }
        req.getRequestDispatcher("/InfoPayment.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.cartSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        String[] productIds = request.getParameterValues("productIds");
        if (productIds == null ) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        session.setAttribute("productIds", productIds);
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}