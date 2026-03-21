package vn.edu.nlu.fit.mythuatshop.Service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.nlu.fit.mythuatshop.Dao.UserDao;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Util.EmailUtil;
import vn.edu.nlu.fit.mythuatshop.Dao.EmailVerificationTokenDao;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.Random;
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

        tokenDao.insert(userId, token, hanXacThuc);

        String verify = baseUrl + "/verify-email?token=" + token;
        String emailTitle = "Xác nhận đăng ký tài khoản";
        String nd = "" + "<p>Chào bạn,</p>" + "<p>Vui lòng nhấn vào link bên dưới để kích hoạt tài khoản:</p>"
                + "<p><a href='" + verify + "'>Kích hoạt tài khoản</a></p>" + "<p>Link này sẽ hết hạn sau 24 giờ.</p>";
        EmailUtil .sendHtml(email, emailTitle, nd);
        return true;
    }
    public boolean verifyEmailToken(String token) {
        if (token == null || token.isBlank()) return false;

        Integer userId = tokenDao.findUserIdIfValid(token);
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

    public boolean resetAndSendEmail(String email) {
        if (email == null){
            return false;
        }
        email = email.trim();

        Users user = userDao.findByEmailFp(email);
        if (user == null) return false;

        String matKhauMoi = generateRandomPassword();
        String hashed = BCrypt.hashpw(matKhauMoi, BCrypt.gensalt(12));

        int row = userDao.updatePasswordByEmail(email, hashed);
        if (row <= 0) return false;

        String subject = "Đặt lại mật khẩu cho tài khoản";
        String nd = "Mật khẩu mới của bạn là: " + matKhauMoi;

        try {
            EmailUtil.send(email, subject, nd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Users findByEmailForGG(String email) {
        if (email == null) return null;
        return userDao.findByEmail(email.trim());
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

}