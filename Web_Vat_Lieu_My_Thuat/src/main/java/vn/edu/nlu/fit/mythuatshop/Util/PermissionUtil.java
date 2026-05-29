package vn.edu.nlu.fit.mythuatshop.Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.io.IOException;
import java.util.Set;

public class PermissionUtil {

    public static boolean hasPermission(HttpServletRequest req, String permissionCode) {
        HttpSession session = req.getSession(false);

        if (session == null) {
            return false;
        }

        Users currentUser = (Users) session.getAttribute("currentUser");

        if (currentUser == null) {
            return false;
        }

        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return true;
        }

        Set<String> permissions = (Set<String>) session.getAttribute("permissions");

        return permissions != null && permissions.contains(permissionCode);
    }

    public static void show404(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        req.getRequestDispatcher("/Error404.jsp").forward(req, resp);
    }
}