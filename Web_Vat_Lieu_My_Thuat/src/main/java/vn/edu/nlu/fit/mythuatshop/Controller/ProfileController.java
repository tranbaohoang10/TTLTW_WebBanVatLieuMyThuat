package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;

import java.io.IOException;

@WebServlet(name = "ProfileController", value = "/profile")
public class ProfileController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Users currentUser = (Users) (session != null ? session.getAttribute("currentUser") : null);

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Users userFromDb = userService.getUserById(currentUser.getId());
        Users userToUse = currentUser;

        if (userFromDb != null) {
            session.setAttribute("currentUser", userFromDb);
            userToUse = userFromDb;
        }

        if (userToUse.getDob() != null) {
            String dobString = userToUse.getDob().toString();
            req.setAttribute("dob", dobString);
        }

        req.getRequestDispatcher("Profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Users currentUser = (Users) (session != null ? session.getAttribute("currentUser") : null);

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phoneNumber = req.getParameter("phoneNumber");
        String dob = req.getParameter("dob");
        String address = req.getParameter("address");

        boolean success = userService.updateProfile(currentUser.getId(), fullName, phoneNumber, dob, address);

        if (!success) {
            req.setAttribute("error", "Cập nhật thông tin thất bại, vui lòng thử lại!");
            req.setAttribute("dob", dob);
            req.getRequestDispatcher("Profile.jsp").forward(req, resp);
        } else {
            Users updatedUser = userService.getUserById(currentUser.getId());
            if (updatedUser != null) {
                session.setAttribute("currentUser", updatedUser);

            }

            resp.sendRedirect(req.getContextPath() + "/profile?success=true");
        }

    }
}