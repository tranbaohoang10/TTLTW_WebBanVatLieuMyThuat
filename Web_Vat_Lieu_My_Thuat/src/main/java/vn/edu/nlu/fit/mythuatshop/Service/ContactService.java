package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ContactDao;
import vn.edu.nlu.fit.mythuatshop.Model.Contact;
import vn.edu.nlu.fit.mythuatshop.Util.EmailUtil;

import java.util.List;

public class ContactService {
    private ContactDao contactDao;

    public ContactService() {
        this.contactDao = new ContactDao();
    }

    public List<Contact> getAllContacts() {
        return contactDao.findAll();
    }

    public void addContact(Contact contact) {
        contactDao.insert(contact);
    }

    public boolean deleteContact(int id) {
        return contactDao.deleteById(id);
    }

    public boolean replyContact(int contactId, String subject, String replyMessage) {
        Contact c = contactDao.findById(contactId);
        if (c == null || c.getEmail() == null || c.getEmail().isBlank()) return false;


        EmailUtil.sendHtml(c.getEmail(), subject, replyMessage);


        return contactDao.updateStatus(contactId, "Đã phản hồi");
    }
    public void sendContactToAdmin(Contact contact) {
        String adminEmail = EmailUtil.getAdminEmail();

        String subject = "Liên hệ mới từ website Mỹ Thuật Shop";

        String html = """
                <h2>Liên hệ mới từ website</h2>
                <p><strong>Họ tên:</strong> %s</p>
                <p><strong>Email:</strong> %s</p>
                <p><strong>Số điện thoại:</strong> %s</p>
                <p><strong>Trạng thái tài khoản:</strong> %s</p>
                <p><strong>Nội dung:</strong></p>
                <div style="padding:12px;border:1px solid #ddd;border-radius:8px;background:#f9f9f9;">
                    %s
                </div>
                """.formatted(
                contact.getFullName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getUserId() != null ? "Đã đăng nhập" : "Chưa đăng nhập",
                contact.getMessage().replace("\n", "<br>")
        );

        EmailUtil.sendHtml(adminEmail, subject, html);
    }
}
