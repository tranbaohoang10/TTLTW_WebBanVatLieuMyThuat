package vn.edu.nlu.fit.mythuatshop.Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;

import java.io.IOException;


@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private UserService userService;
    private static final String loginFailCount = "login_fail_count";


    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        if(email == null || password == null || email.isBlank() || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập email và mật khẩu.");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }

        Users user = userService.getUserByEmail(email.trim());
        if(user == null) {
            req.setAttribute("error", "Sai email hoặc mật khẩu");
            req.setAttribute("email", email);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }

        if(user.getIsActive()==3){
            req.setAttribute("error", "Tài khoản đã bị khóa.");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }

        if(user.getIsActive()==0){
            req.setAttribute("warning", "Hãy truy cập email để xác nhận tài khoản.");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        Users user1 = userService.login(email.trim(), password);
        if (user1 == null) {
            increaseFail(session);
            req.setAttribute("error", "Sai email hoặc mật khẩu");
            req.setAttribute("email", email);
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }
        session.removeAttribute(loginFailCount);
        session.setAttribute("currentUser", user1);
        session.setMaxInactiveInterval(30*60);

        Cart cart;
        Object  obj = session.getAttribute("cart");
        if (obj instanceof Cart) {
            cart = (Cart) obj;
        }
        else {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        session.setAttribute("cartCount", cart.getTotalQuantity());

        String role = user1.getRole();
        if(role.equalsIgnoreCase("admin")){
            resp.sendRedirect(req.getContextPath()+"/admin/overview");
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/home");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String error = (String) session.getAttribute("FLASH_ERROR");

        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("FLASH_ERROR");
        }

        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
    }
    public void increaseFail(HttpSession session){
        Integer failCount = (Integer) session.getAttribute(loginFailCount);
        if(failCount==null){
            failCount = 0;
        }
        failCount++;
        session.setAttribute(loginFailCount,failCount);
        System.out.println("Số lần đăng nhập sai trong session: " + failCount);
    }
}