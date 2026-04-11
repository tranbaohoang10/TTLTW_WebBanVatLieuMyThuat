package vn.edu.nlu.fit.mythuatshop.Service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.nlu.fit.mythuatshop.Dao.UserDao;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AdminUserService {
    private final UserDao userDao = new UserDao();

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
                              String dobStr, String address, String role) {
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

        String rawPassword = UUID.randomUUID().toString().substring(0, 8);
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        user.setIsActive(0);

        return userDao.adminCreateUser(user) > 0;
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
}
