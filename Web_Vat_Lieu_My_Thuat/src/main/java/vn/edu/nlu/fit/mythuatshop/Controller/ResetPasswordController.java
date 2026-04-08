package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;

import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        if (token == null || token.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login?reset=invalid");
            return;
        }

        Integer userId = userService.getUserIdByValidResetToken(token);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login?reset=invalid");
            return;
        }

        req.setAttribute("token", token);
        req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (token == null || token.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login?reset=invalid");
            return;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập mật khẩu mới!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
            return;
        }

        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$")) {
            req.setAttribute("error", "Mật khẩu mới phải có ít nhất 8 ký tự, có chữ hoa, chữ thường và ký tự đặc biệt.");
            req.setAttribute("token", token);
            req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
            return;
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập xác nhận mật khẩu!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Xác nhận mật khẩu không khớp!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
            return;
        }

        boolean ok = userService.resetPasswordByToken(token, newPassword);

        if (!ok) {
            req.setAttribute("error", "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("resetSuccess", "Đặt lại mật khẩu thành công!");
        req.getRequestDispatcher("ResetPassword.jsp").forward(req, resp);
    }
}