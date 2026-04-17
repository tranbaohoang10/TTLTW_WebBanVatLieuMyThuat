package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.CartItem;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddToCartController", value = "/AddToCart")
public class AddToCartController extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if ("remove".equals(action)) {
            handleRemoveItem(request, response, session);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if ("ajaxUpdate".equals(action)) {
            handleAjaxUpdate(request, response, session);
        } else {
            handleAddToCart(request, response, session);
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session) throws IOException {

        String pidStr = request.getParameter("productId");
        String qtyStr = request.getParameter("quantity");
        String redirectTo = request.getParameter("redirectTo");

        if (pidStr == null || qtyStr == null || pidStr.isBlank() || qtyStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        int productId = Integer.parseInt(pidStr);
        int quantity = Integer.parseInt(qtyStr);
        if (quantity < 1) quantity = 1;

        Product p = productService.getProductByIdActive(productId);
        if (p == null || p.getQuantityStock() <= 0) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        if (quantity > p.getQuantityStock()) {
            quantity = p.getQuantityStock();
        }

        Cart cart = getOrCreateCart(session);

        CartItem cartItem = new CartItem(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDiscountDefault(),
                p.getThumbnail(),
                quantity,
                p.getQuantityStock()
        );

        cart.addCartItem(cartItem);

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if (isAjax) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"success\":true,\"cartCount\":" + cart.getTotalQuantity() + "}");
        } else {
            if ("cart".equals(redirectTo)) {
                response.sendRedirect(request.getContextPath() + "/cart");
            } else {
                String referer = request.getHeader("referer");
                response.sendRedirect(referer != null ? referer : (request.getContextPath() + "/cart"));
            }
        }
    }

    private void handleRemoveItem(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException {

        String pidStr = request.getParameter("productId");
        if (pidStr == null || pidStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int productId = Integer.parseInt(pidStr);

        Cart cart = getOrCreateCart(session);
        cart.removeCartItem(productId);

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void handleAjaxUpdate(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        String pidStr = request.getParameter("productId");
        String qtyStr = request.getParameter("quantity");

        if (pidStr == null || qtyStr == null || pidStr.isBlank() || qtyStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false}");
            return;
        }

        int productId = Integer.parseInt(pidStr);
        int quantity = Integer.parseInt(qtyStr);
        if (quantity < 1) quantity = 1;

        Product product = productService.getProductByIdActive(productId);
        Cart cart = getOrCreateCart(session);

        if (product == null || product.getQuantityStock() <= 0) {
            cart.removeCartItem(productId);
            session.setAttribute("cart", cart);
            session.setAttribute("cartCount", cart.getTotalQuantity());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false}");
            return;
        }

        if (quantity > product.getQuantityStock()) {
            quantity = product.getQuantityStock();
        }

        cart.updateQuantity(productId, quantity);

        long totalAmount = 0;
        long itemSubtotal = 0;

        for (CartItem item : cart.getCarts().values()) {
            long sub = (long) item.totalPriceCartItem();
            totalAmount += sub;

            if (item.getProductId() == productId) {
                itemSubtotal = sub;
            }
        }

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        PrintWriter out = response.getWriter();
        out.write("{\"success\":true,"
                + "\"itemSubtotal\":" + itemSubtotal + ","
                + "\"totalAmount\":" + totalAmount + ","
                + "\"cartCount\":" + cart.getTotalQuantity() + "}");
    }

    private Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}