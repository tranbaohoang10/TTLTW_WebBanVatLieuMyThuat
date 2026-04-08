package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.CartItem;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.io.IOException;

@WebServlet(name = "CheckoutController", value = "/checkout")
public class CheckoutController extends HttpServlet {

    private ProductService productService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }
        Cart cartTemp = (Cart) session.getAttribute("cartTemp");
        if (cartTemp == null || cartTemp.cartSize() == 0) {
            resp.sendRedirect(req.getContextPath() + "/cart");
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
        String checkoutMode = request.getParameter("checkoutMode");


        if ("buyNow".equals(checkoutMode)) {
            String productIdStr = request.getParameter("productId");
            String quantityStr = request.getParameter("quantity");

            if (productIdStr == null || quantityStr == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (quantity < 1) quantity = 1;

            Product product = productService.getProductByIdActive(productId);
            if (product == null || product.getQuantityStock() <= 0) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            if (quantity > product.getQuantityStock()) {
                quantity = product.getQuantityStock();
            }

            Cart cartTemp = new Cart();
            CartItem item = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscountDefault(),
                    product.getThumbnail(),
                    quantity,
                    product.getQuantityStock()
            );

            cartTemp.addCartItem(item);
            session.setAttribute("cartTemp", cartTemp);

            response.sendRedirect(request.getContextPath() + "/checkout");
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
        Cart cartTemp = cart.getCartByIds(productIds);
        if (cartTemp.cartSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        session.setAttribute("cartTemp", cartTemp);
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}