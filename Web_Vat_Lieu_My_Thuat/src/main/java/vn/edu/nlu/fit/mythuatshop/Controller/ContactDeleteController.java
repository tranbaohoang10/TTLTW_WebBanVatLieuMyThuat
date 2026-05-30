package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ContactService;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;

@WebServlet(name = "ContactDeleteController", value = "/admin/contacts/delete")
public class ContactDeleteController extends HttpServlet {
    private ContactService contactService = new ContactService();
    private final LogService logService = new LogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idRaw = req.getParameter("id");
        if (!PermissionUtil.hasPermission(req, "CONTACT_DELETE")) {
            PermissionUtil.showNoPermission(req, resp);
            return;
        }

        if (idRaw != null) {
            try {
                int id = Integer.parseInt(idRaw);
                contactService.deleteContact(id);

                writeLog(req, "Xóa liên hệ", "Quản lý liên hệ", "ID liên hệ: " + id, null);
                req.getSession().setAttribute("toast", "Đã xóa liên hệ #" + id);
            } catch (NumberFormatException ignored) {
                req.getSession().setAttribute("toast", "ID không hợp lệ!");
            }
        } else {
            req.getSession().setAttribute("toast", "Thiếu ID!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/contacts");
    }
    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if (obj instanceof Users) {
            return ((Users) obj).getId();
        }
        return null;
    }
    private void writeLog(HttpServletRequest request, String label, String location, Object beforeData, Object afterData) {
        Integer userId = getCurrentUserId(request);
        if (userId != null) {
            logService.log(label, userId, location, beforeData, afterData);
        }
    }
}