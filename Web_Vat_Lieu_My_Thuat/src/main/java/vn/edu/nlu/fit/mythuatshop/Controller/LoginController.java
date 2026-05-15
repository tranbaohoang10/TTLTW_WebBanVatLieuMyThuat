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
    private static final String login_lock = "login_lock";
    private static final int total_Fail = 5;
    private static final long time_temp_lock = 15*60*1000L;


    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        Long lockUntil =  (Long)session.getAttribute(login_lock);
        long now = System.currentTimeMillis();
        if(lockUntil!=null ){
            if(now < lockUntil){
                long remainMs = lockUntil - now;
                long remainMinute =  remainMs / 60000;
                    req.setAttribute("error", "Tài khoản bị khóa vì nhập sai mật khẩu quá 5 lần. "+"Vui lòng thử lại sau " + remainMinute +" phút. ");
                req.setAttribute("email", email);
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
                return;
            }
            else {
                session.removeAttribute(login_lock);
                session.removeAttribute(loginFailCount);
            }
        }


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

        Users user1 = userService.login(email.trim(), password);
        if (user1 == null) {
            increaseFail(session);
            Integer failCount = (Integer) session.getAttribute(loginFailCount);
            if(failCount!=null && failCount>=total_Fail) {
                long createLock = System.currentTimeMillis() + time_temp_lock;
                session.setAttribute(login_lock, createLock);
                req.setAttribute("error", "Bạn đã nhập sai mật khẩu quá 5 lần. Tài khoản đã bị khóa trong 15 phút");
            }
            else {
                int remaining = total_Fail - failCount;
                req.setAttribute("error", "Sai email hoặc mật khẩu. " + "Bạn còn " + remaining + " lần thử nhập nữa.");
            }

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
        if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")) {
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