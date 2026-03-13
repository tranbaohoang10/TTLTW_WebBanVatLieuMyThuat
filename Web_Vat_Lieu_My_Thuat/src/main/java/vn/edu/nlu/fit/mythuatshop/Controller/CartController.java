package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.CartItem;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.io.IOException;
@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        Cart  cart = (Cart) session.getAttribute("cart");
        if(cart == null){
             cart = new Cart();
            session.setAttribute("cart", cart);
        }
        int totalQuantityProducts = cart.getTotalQuantity();
        double totalPrice = 0;
        for (CartItem item : cart.getCarts().values()){
            // tổng tiền của 1 cartitem(số lượng và quantity)
            totalPrice += item.totalPriceCartItem();
        }
        request.setAttribute("cartItem",cart.getCarts().values());
        request.setAttribute("totalQuantityProducts",totalQuantityProducts);
        request.setAttribute("totalPrice",totalPrice);
        request.setAttribute("cartSize", cart.cartSize());
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
    }
}
