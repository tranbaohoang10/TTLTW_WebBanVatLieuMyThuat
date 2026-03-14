package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;

import java.io.IOException;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");

        if (phoneNumber == null || !phoneNumber.matches("^0\\d{9}$")) {
            req.setAttribute("error", "Số điện thoại không hợp lệ (vd: 0912345678)");
            req.getRequestDispatcher("Register.jsp").forward(req, resp);
            return;
        }


        if(password==null || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$")){
            req.setAttribute("error", "Mật khẩu có ít nhất 8 kí tự gồm chữ hoa, chũ thường và các kí tự đặc biệt.");
            req.getRequestDispatcher("Register.jsp").forward(req, resp);
            return;
        }

        String baseUrl = req.getScheme() + "://" + req.getServerName()
                + ((req.getServerPort() == 80 || req.getServerPort() == 443) ? "" : (":" + req.getServerPort()))
                + req.getContextPath();

        boolean success = userService.register(fullName, email, phoneNumber, password, address, baseUrl);
        if(!success){
            req.setAttribute("error", "Email đã được sử dụng, vui lòng nhập email khác!");
            req.getRequestDispatcher("Register.jsp").forward(req,resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/register?success=true&verify=sent");

    }

}
