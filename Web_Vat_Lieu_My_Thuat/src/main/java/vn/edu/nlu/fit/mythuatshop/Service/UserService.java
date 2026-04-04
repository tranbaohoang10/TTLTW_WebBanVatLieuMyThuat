package vn.edu.nlu.fit.mythuatshop.Service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.nlu.fit.mythuatshop.Dao.UserDao;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Util.EmailUtil;
import vn.edu.nlu.fit.mythuatshop.Dao.EmailVerificationTokenDao;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.UUID;

public class UserService {
    private final UserDao userDao;
    private final EmailVerificationTokenDao tokenDao = new EmailVerificationTokenDao();

    public UserService() {
        this.userDao = new UserDao();
    }


    public Users login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        email = email.trim();
        Users user = userDao.findByEmail(email);
        if (user == null) {
            return null;
        }
        if(user.getIsActive()==0){
            return null;
        }
        if(user.getIsActive()==3){
            return null;
        }
        boolean checkPass = BCrypt.checkpw(password, user.getPassword());
        if (!checkPass) {
            return null;
        }
        return user;
    }


    public boolean register( String fullName, String email, String phoneNumber, String password, String baseUrl) {
        if(email == null || email.isBlank()){
            return false;
        }
        if(password == null || password.isBlank()){
            return false;
        }
        email = email.trim();
        Users checkUser = userDao.findByEmail(email);
        if(checkUser != null){
           if(checkUser.getIsActive()==1){
               return false;
           }
           if(checkUser.getIsActive()==0){
               String oldToken = tokenDao.findValidTokenByUserId(checkUser.getId(), "VERIFY_EMAIL");
               if(oldToken==null){
                   return false;
               }
               String newToken = UUID.randomUUID().toString().replace("-", "");
               LocalDateTime hanXT = LocalDateTime.now().plusHours(24);

               tokenDao.deleteTokensByUserId(checkUser.getId(), "VERIFY_EMAIL");
               tokenDao.insert(checkUser.getId(), newToken,  hanXT, "VERIFY_EMAIL");

               String verify = baseUrl + "/verify-email?token=" + newToken;
               String emailTitle = "Xác nhận đăng ký tài khoản";
               String nd = "" + "<p>Chào bạn,</p>" + "<p>Bạn đã yêu cầu gửi lại email xác thực tài khoản.</p>"
                       + "<p>Vui lòng nhấn vào link bên dưới để kích hoạt tài khoản:</p>" + "<p><a href='" + verify
                       + "'>Kích hoạt tài khoản</a></p>" + "<p>Link này sẽ hết hạn sau 24 giờ.</p>";
               EmailUtil .sendHtml(email, emailTitle, nd);
               return true;
           }
           return false;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Users newUser = new Users();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRole("USER");
        newUser.setIsActive(0);

        int userId = userDao.insertUserAndReturnId(newUser);
        if(userId <=0){
            return false;
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime hanXacThuc =  LocalDateTime.now().plusHours(24);

        tokenDao.insert(userId, token, hanXacThuc, "VERIFY_EMAIL");

        String verify = baseUrl + "/verify-email?token=" + token;
        String emailTitle = "Xác nhận đăng ký tài khoản";
        String nd = "" + "<p>Chào bạn,</p>" + "<p>Vui lòng nhấn vào link bên dưới để kích hoạt tài khoản:</p>"
                + "<p><a href='" + verify + "'>Kích hoạt tài khoản</a></p>" + "<p>Link này sẽ hết hạn sau 24 giờ.</p>";
        EmailUtil .sendHtml(email, emailTitle, nd);
        return true;
    }
    public boolean verifyEmailToken(String token) {
        if (token == null || token.isBlank()) return false;

        Integer userId = tokenDao.findUserIdIfValid(token, "VERIFY_EMAIL");
        if (userId == null) return false;

        userDao.setActive(userId, 1);
        tokenDao.markUsed(token);
        return true;
    }

    public Users getUserById(int id) {
        return userDao.findById(id);
    }

    public boolean updateProfile(int userId, String fullName, String phoneNumber, String dobStr, String address){
        Users user = userDao.findById(userId);
        if(user == null){
            return false;
        }
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);

        if (dobStr != null && !dobStr.isEmpty()) {
            LocalDate dob = LocalDate.parse(dobStr);
            user.setDob(dob);
        }

        int result = userDao.updateUser(user);
        return result >0;
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        Users user = userDao.findById(userId);

        String currentHash = user.getPassword();
        boolean match = BCrypt.checkpw(oldPassword, currentHash);
        if (!match) return false;

        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        return userDao.updatePassword(userId, newHash);
    }

    public Users getUserByEmail(String email) {
        return userDao.findByEmailFp(email);
    }


    public Users findByEmailForGG(String email) {
        if (email == null) return null;
        return userDao.findByEmail(email.trim());
    }

    public Integer getUserIdByValidResetToken(String token) {
        if (token == null || token.isBlank()) return null;
        return tokenDao.findUserIdIfValid(token, "RESET_PASSWORD");
    }

    public boolean sendResetPasswordLink(String email, String baseUrl) {
        if (email == null || email.isBlank()) return false;
        email = email.trim();

        Users user = userDao.findByEmailFp(email);
        if (user == null) return false;

        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        tokenDao.deleteTokensByUserId(user.getId(), "RESET_PASSWORD");
        tokenDao.insert(user.getId(), token, expiresAt, "RESET_PASSWORD");

        String resetLink = baseUrl + "/reset-password?token=" + token;

        String subject = "Yêu cầu đặt lại mật khẩu";
        String html = ""
                + "<p>Chào bạn,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản MyThuatShop.</p>"
                + "<p>Vui lòng nhấn vào link bên dưới để đặt lại mật khẩu:</p>"
                + "<p><a href='" + resetLink + "'>Đặt lại mật khẩu</a></p>"
                + "<p>Link này sẽ hết hạn sau 30 phút.</p>";
        EmailUtil.sendHtml(email, subject, html);
        return true;
    }
    public boolean resetPasswordByToken(String token, String newPassword) {
        if (token == null || token.isBlank()) return false;
        if (newPassword == null || newPassword.isBlank()) return false;

        Integer userId = tokenDao.findUserIdIfValid(token, "RESET_PASSWORD");
        if (userId == null) return false;

        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        boolean updated = userDao.updatePassword(userId, newHash);

        if (!updated) return false;

        tokenDao.markUsed(token);
        return true;
    }
    public Users registerGoogleUser(String name, String email) {
        if (email == null || email.isBlank()) return null;
        email = email.trim();

        Users userExisted = userDao.findByEmail(email);
        if (userExisted != null) return userExisted;

        String randomPassword = UUID.randomUUID().toString();
        String hashed = BCrypt.hashpw(randomPassword, BCrypt.gensalt(12));

        Users user = new Users();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(hashed);
        user.setRole("USER");
        user.setPhoneNumber("");
        user.setAddress("");
        user.setIsActive(1);

        userDao.insertUser(user);
        return userDao.findByEmail(email);
    }

    public void registerGoogleUserSafe(String name, String email) {
        if (email == null || email.isBlank()) return;
        email = email.trim();

        if (userDao.findByEmail(email) != null) return;

        Users user = new Users();
        user.setFullName(name != null && !name.isBlank() ? name : "Google User");
        user.setEmail(email);

        String raw = UUID.randomUUID().toString();
        String hashed = BCrypt.hashpw(raw, BCrypt.gensalt(12));
        user.setPassword(hashed);

        user.setPhoneNumber("");
        user.setAddress("");
        user.setRole("USER");
        user.setIsActive(1);

        userDao.insertUser(user);
    }
    public Users updateIsActiveWhenLoginGG(String email){
        if (email == null || email.isBlank()) return null;

        Users user = userDao.findByEmail(email.trim());
        if (user == null) return null;

        if (user.getIsActive() == 3) {
            return user;
        }
        if (user.getIsActive() == 0) {
            userDao.setActive(user.getId(), 1);
            user.setIsActive(1);
        }

        return user;
    }

}