package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }

        String action = req.getParameter("action");
        if ("remove".equals(action)) {
            handleRemoveItem(req, resp, session);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private void handleRemoveItem(HttpServletRequest req, HttpServletResponse resp,
                                  HttpSession session) throws IOException {

        String pidStr = req.getParameter("productId");
        if (pidStr == null || pidStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        int pid = Integer.parseInt(pidStr);

        Cart cart = getOrCreateCart(session);
        cart.removeCartItem(pid);
        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }

        String action = req.getParameter("action");
        if ("update".equals(action)) {
            // nếu sau này còn dùng form submit
            handleUpdateCart(req, resp, session);
        } else if ("ajaxUpdate".equals(action)) {
            // cập nhật số lượng bằng AJAX
            handleAjaxUpdate(req, resp, session);
        } else {
            // mặc định: thêm vào giỏ (trang chi tiết sản phẩm)
            handleAddToCart(req, resp, session);
        }
    }

    // ========= THÊM 1 SẢN PHẨM =========
    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp,
                                 HttpSession session) throws IOException {

        String pidStr = req.getParameter("productId");
        String qtyStr = req.getParameter("quantity");

        if (pidStr == null || pidStr.isBlank() ||
                qtyStr == null || qtyStr.isBlank()) {
            String ref = req.getHeader("referer");
            resp.sendRedirect(ref != null ? ref : (req.getContextPath() + "/home"));
            return;
        }

        int productId = Integer.parseInt(pidStr);
        int quantity = Integer.parseInt(qtyStr);
        if (quantity <= 0) quantity = 1;

        Product p = productService.getProductByIdActive(productId); // ✅ chỉ lấy active
        if (p == null) {
            // sản phẩm không tồn tại hoặc đã bị khóa
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"success\":false,\"message\":\"Sản phẩm đã ngừng bán\"}");
                return;
            }
            String ref = req.getHeader("referer");
            resp.sendRedirect(ref != null ? ref : (req.getContextPath() + "/home"));
            return;
        }

// (khuyến nghị) chặn nếu hết hàng
        if (p.getQuantityStock() <= 0) {
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"success\":false,\"message\":\"Sản phẩm đã hết hàng\"}");
                return;
            }
            String ref = req.getHeader("referer");
            resp.sendRedirect(ref != null ? ref : (req.getContextPath() + "/home"));
            return;
        }

        Cart cart = getOrCreateCart(session);
        CartItem cartItem = new CartItem(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDiscountDefault(),
                p.getThumbnail(),
                quantity
        );
        cart.addCartItem(cartItem);

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        // Kiểm tra nếu là AJAX request
        String ajaxHeader = req.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if (isAjax) {
            // Trả về JSON response cho AJAX
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write("{\"success\":true,\"cartCount\":" + cart.getTotalQuantity() + "}");
        } else {
            // Redirect thông thường
            String referer = req.getHeader("referer");
            resp.sendRedirect(referer != null ? referer : (req.getContextPath() + "/cart"));
        }
    }

    // ========= UPDATE TOÀN BỘ GIỎ (NẾU CÒN DÙNG FORM) =========
    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp,
                                  HttpSession session) throws IOException {

        String[] productIds = req.getParameterValues("productId");
        String[] quantities = req.getParameterValues("quantity");

        if (productIds == null || quantities == null ||
                productIds.length == 0 || productIds.length != quantities.length) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        Cart cart = getOrCreateCart(session);
        cart.getCarts().clear();

        for (int i = 0; i < productIds.length; i++) {
            int pid,qty;
            try {
                pid = Integer.parseInt(productIds[i]);
                qty = Integer.parseInt(quantities[i]);
            } catch (NumberFormatException e) {
                continue;
            }

            if (qty < 1) qty = 1;
            Product p = productService.getProductByIdActive(pid);
            if (p == null) continue; // hàng bị khóa => bỏ qua (không add lại vào cart)

            CartItem ci = new CartItem(p.getId(), p.getName(), p.getPrice(),
                    p.getDiscountDefault(), p.getThumbnail(), qty);
            cart.addCartItem(ci);
        }

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getTotalQuantity());

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    // ========= UPDATE 1 SẢN PHẨM (AJAX) =========
    private void handleAjaxUpdate(HttpServletRequest req, HttpServletResponse resp,
                                  HttpSession session) throws IOException {

        resp.setContentType("application/json; charset=UTF-8");

        String pidStr = req.getParameter("productId");
        String qtyStr = req.getParameter("quantity");

        if (pidStr == null || pidStr.isBlank() ||
                qtyStr == null || qtyStr.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\":false}");
            return;
        }

        int productId = Integer.parseInt(pidStr);
        int quantity = Integer.parseInt(qtyStr);
        if (quantity < 1) quantity = 1;


        Product pCheck = productService.getProductByIdActive(productId);
        Cart cart = getOrCreateCart(session);

        if (pCheck == null) {
            // sản phẩm đã bị khóa => remove khỏi giỏ
            cart.removeCartItem(productId);
            session.setAttribute("cart", cart);

            int cartCount = cart.getTotalQuantity();
            session.setAttribute("cartCount", cartCount);

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"success\":false,\"message\":\"Sản phẩm đã ngừng bán\",\"cartCount\":" + cartCount + "}");
            return;
        }

// (khuyến nghị) giới hạn theo tồn kho
        if (pCheck.getQuantityStock() <= 0) {
            cart.removeCartItem(productId);
            session.setAttribute("cart", cart);

            int cartCount = cart.getTotalQuantity();
            session.setAttribute("cartCount", cartCount);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\":false,\"message\":\"Sản phẩm đã hết hàng\",\"cartCount\":" + cartCount + "}");
            return;
        }
        if (quantity > pCheck.getQuantityStock()) quantity = pCheck.getQuantityStock();
        if (quantity < 1) quantity = 1;


        cart.updateQuantity(productId, quantity);
        session.setAttribute("cart", cart);


        long totalAmount = 0;
        long itemSubtotal = 0;

        java.util.List<Integer> toRemove = new java.util.ArrayList<>();

        for (CartItem item : cart.getCarts().values()) {
            Product p = productService.getProductByIdActive(item.getProductId());
            if (p == null) {
                toRemove.add(item.getProductId());
                continue;
            }

            long price = (long) p.getPriceAfterDiscount();
            long sub = price * item.getQuantity();
            totalAmount += sub;

            if (item.getProductId() == productId) {
                itemSubtotal = sub;
            }
        }

        for (Integer rid : toRemove) cart.removeCartItem(rid);
        session.setAttribute("cart", cart);


        int cartCount = cart.getTotalQuantity();
        session.setAttribute("cartCount", cartCount);

        PrintWriter out = resp.getWriter();
        out.write("{\"success\":true,"
                + "\"itemSubtotal\":" + itemSubtotal + ","
                + "\"totalAmount\":" + totalAmount + ","
                + "\"cartCount\":" + cartCount + "}");
    }

    // ========= HELPER METHOD =========
    private Cart getOrCreateCart(HttpSession session) {
        Object cartObj = session.getAttribute("cart");

        // Nếu cart cũ là ArrayList (từ code cũ), reset về Cart mới
        if (cartObj instanceof java.util.ArrayList) {
            Cart newCart = new Cart();
            session.setAttribute("cart", newCart);
            return newCart;
        }

        // Nếu đã là Cart, trả về
        if (cartObj instanceof Cart) {
            return (Cart) cartObj;
        }

        // Nếu null hoặc kiểu khác, tạo mới
        Cart newCart = new Cart();
        session.setAttribute("cart", newCart);
        return newCart;
    }
}
