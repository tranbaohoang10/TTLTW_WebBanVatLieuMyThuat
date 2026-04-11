package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.CartItem;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {
    private ProductService productService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

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
        List<String> removedNames = cart.removeOutOfStockItems(productService);

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        if (!removedNames.isEmpty()) {
            session.setAttribute("cartWarning",
                    "Sản phẩm hiện tại đã ngừng bán hoặc hết hàng.");
        }
        int totalQuantityProducts = cart.getTotalQuantity();
        double totalAmount = 0;
        for (CartItem item : cart.getCarts().values()){

            totalAmount += item.totalPriceCartItem();
        }
        request.setAttribute("cartItems",cart.getCarts().values());
        request.setAttribute("totalQuantityProducts",totalQuantityProducts);
        request.setAttribute("totalAmount",totalAmount);
        request.setAttribute("cartSize", cart.cartSize());
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
    }
}
