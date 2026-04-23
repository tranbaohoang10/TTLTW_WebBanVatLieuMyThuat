package vn.edu.nlu.fit.mythuatshop.Service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.nlu.fit.mythuatshop.Dao.UserDao;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Dao.EmailVerificationTokenDao;
import vn.edu.nlu.fit.mythuatshop.Util.EmailUtil;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AdminUserService {
    private final UserDao userDao = new UserDao();
    private final EmailVerificationTokenDao tokenDao = new EmailVerificationTokenDao();

    public List<Users> listUsers(int page, int pageSize, String keyword) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;
        if (keyword == null) {
            keyword = "";
        } else {
            keyword = keyword.trim();
        }
        return userDao.findUsers(keyword, offset, pageSize);
    }

    public int totalPages(int pageSize, String keyword) {
        if (keyword == null) {
            keyword = "";
        } else {
            keyword = keyword.trim();
        }
        int totalUsers = userDao.countUsers(keyword);
        return (int) Math.ceil((double) totalUsers / pageSize);
    }

    public boolean createUser(String fullName, String email, String phone,
                              String dobStr, String address, String role, String baseUrl) {
        if (email == null || email.isBlank()) {
            return false;
        }
        email = email.trim();
        if (userDao.findByEmail(email) != null) {
            return false;
        }

        Users user = new Users();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address);

        if (role == null) {
            user.setRole("USER");
        } else {
            user.setRole(role.toUpperCase());}
        if (dobStr != null && !dobStr.isBlank()) {
            user.setDob(LocalDate.parse(dobStr));
        }

        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "@A";
        user.setPassword(BCrypt.hashpw(tempPassword, BCrypt.gensalt(12)));

        user.setIsActive(0);

        int userId = userDao.adminCreateUser(user);
        if (userId <= 0) return false;

        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        tokenDao.deleteTokensByUserId(userId, "RESET_PASSWORD");
        tokenDao.insert(userId, token, expiresAt, "RESET_PASSWORD");

        String resetLink = baseUrl + "/reset-password?token=" + token;

        String subject = "Đặt mật khẩu tài khoản";
        String html = ""
                + "<p>Chào bạn,</p>"
                + "<p>Tài khoản của bạn đã được admin tạo trên hệ thống.</p>"
                + "<p>Vui lòng nhấn vào link bên dưới để thiết lập mật khẩu:</p>"
                + "<p><a href='" + resetLink + "'>Thiết lập mật khẩu</a></p>"
                + "<p>Link này sẽ hết hạn sau 30 phút.</p>";

        EmailUtil.sendHtml(email, subject, html);
        return true;

    }

    public boolean updateUser(int id, String fullName, String phone,
                              String dobStr, String address, String role) {
        Users user = userDao.findById(id);
        if (user == null) {
            return false;
        }

        user.setFullName(fullName);
        user.setPhoneNumber(phone);
        user.setAddress(address);

        if (role != null) {
            user.setRole(role.toUpperCase());
        }
        if (dobStr != null && !dobStr.isBlank()) {
            user.setDob(LocalDate.parse(dobStr));
        } else {
            user.setDob(null);
        }
        return userDao.adminUpdateUser(user) > 0;
    }

    public boolean lockUser(int id) {
        return userDao.setActive(id, 3) > 0;
    }


    public boolean unlockUser(int id) {
        return userDao.setActive(id, 1) > 0;
    }

    public Users getUserById(int id) {
        return userDao.findById(id);
    }
}
