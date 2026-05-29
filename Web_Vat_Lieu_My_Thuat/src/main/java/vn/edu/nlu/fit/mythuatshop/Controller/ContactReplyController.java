package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.ContactService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;

@WebServlet(name = "ContactReplyController", urlPatterns = {"/admin/contacts/reply"})
public class ContactReplyController extends HttpServlet {
    private ContactService contactService = new ContactService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (!PermissionUtil.hasPermission(req, "CONTACT_REPLY_VIEW")) {
            PermissionUtil.show404(req, resp);
            return;
        }

        String idRaw = req.getParameter("id");
        String subject = req.getParameter("subject");
        String replyMessage = req.getParameter("replyMessage");

        if (subject == null || subject.isBlank()) {
            subject = "Phản hồi liên hệ - MyThuatShop";
        }

        if (idRaw == null || idRaw.isBlank() || replyMessage == null || replyMessage.isBlank()) {
            req.getSession().setAttribute("toast", "Vui lòng nhập nội dung phản hồi!");
            resp.sendRedirect(req.getContextPath() + "/admin/contacts");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("toast", "ID không hợp lệ!");
            resp.sendRedirect(req.getContextPath() + "/admin/contacts");
            return;
        }

        boolean ok = contactService.replyContact(id, subject, replyMessage);

        if (ok) {
            req.getSession().setAttribute("toast", "Gửi phản hồi thành công!");
        } else {
            req.getSession().setAttribute("toast", "Gửi phản hồi thất bại (không tìm thấy liên hệ hoặc email rỗng).");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/contacts");
    }
}
