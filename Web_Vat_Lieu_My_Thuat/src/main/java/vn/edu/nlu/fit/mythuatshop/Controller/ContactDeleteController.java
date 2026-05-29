package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.ContactService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;

@WebServlet(name = "ContactDeleteController", value = "/admin/contacts/delete")
public class ContactDeleteController extends HttpServlet {
    private ContactService contactService = new ContactService();

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

                // flash message (đơn giản): lưu vào session
                req.getSession().setAttribute("toast", "Đã xóa liên hệ #" + id);
            } catch (NumberFormatException ignored) {
                req.getSession().setAttribute("toast", "ID không hợp lệ!");
            }
        } else {
            req.getSession().setAttribute("toast", "Thiếu ID!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/contacts");
    }

}