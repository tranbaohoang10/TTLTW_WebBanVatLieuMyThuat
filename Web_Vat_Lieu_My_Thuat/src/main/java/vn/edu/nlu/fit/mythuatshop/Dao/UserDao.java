package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class UserDao {
    private final Jdbi jdbi;
    public UserDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public Users findByEmail(String email) {
        String sql = "SELECT id, fullName, email, `password`, phoneNumber, dob, address, role, createAt, isActive" +
                " FROM Users WHERE email = :email ";
        return jdbi.withHandle(handle -> handle.createQuery(sql).bind("email", email).mapToBean(Users.class).findOne().orElse(null));
    }
    public int insertUser(Users user) {
        String sql = "INSERT INTO users (fullName, email, password, phoneNumber, address, role, createAt, isActive) " +
                "VALUES (:fullName, :email, :password, :phoneNumber, :address, :role, CURRENT_TIMESTAMP(), :isActive)";
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("fullName", user.getFullName())
                .bind("email", user.getEmail())
                .bind("password", user.getPassword())
                .bind("phoneNumber", user.getPhoneNumber())
                .bind("address", user.getAddress())
                .bind("role", user.getRole())
                .bind("isActive", user.getIsActive())
                .execute()
        );
    }
    public int updateUser(Users user) {
        String sql = "UPDATE users " +
                "SET fullName = :fullName, " +
                "    phoneNumber = :phoneNumber, " +
                "    dob = :dob, " +
                "    address = :address " +
                "WHERE id = :id";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("fullName", user.getFullName())
                        .bind("phoneNumber", user.getPhoneNumber())
                        .bind("dob", user.getDob() != null ? java.sql.Date.valueOf(user.getDob()) : null)
                        .bind("address", user.getAddress())
                        .bind("id", user.getId())
                        .execute()
        );
    }
    public Users findById(int id) {
        String sql = "SELECT id, fullName, email, `password`, phoneNumber, dob, address, role, createAt, isActive " +
                "  FROM users WHERE id = :id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Users.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean updatePassword(int userId, String newHash) {
        String sql = "UPDATE users SET password = :matkhaumoi WHERE id = :id";
        int row = jdbi.withHandle(handle ->handle.createUpdate(sql)
                .bind("matkhaumoi", newHash)
                .bind("id", userId)
                .execute());
        return row > 0;
    }
    public Users findByEmailFp(String email){
        String sql = "SELECT id, fullName, email, password, phoneNumber, dob, address, role, createAt, isActive" +
                " FROM users WHERE email = :email ";
        return jdbi.withHandle(handle ->handle.createQuery(sql).bind("email", email).mapToBean(Users.class).findOne().orElse(null));
    }

    public List<Users> findUsers(String keyword, int offset, int limit) {

        boolean hasKw = keyword != null && !keyword.trim().isEmpty();

        String sql =
                "SELECT id, fullName, email, phoneNumber, dob, address, role, createAt, isActive " +
                        "FROM users " +
                        (hasKw ? "WHERE fullName LIKE :kw OR email LIKE :kw OR phoneNumber LIKE :kw " : "") +
                        "ORDER BY createAt DESC " +
                        "LIMIT :limit OFFSET :offset";

        final String finalSql = sql;
        final String finalKw = hasKw ? "%" + keyword.trim() + "%" : null;

        return jdbi.withHandle(h -> {
            var q = h.createQuery(finalSql)
                    .bind("limit", limit)
                    .bind("offset", offset);

            if (hasKw) q.bind("kw", finalKw);

            return q.mapToBean(Users.class).list();
        });
    }


    public int countUsers(String keyword) {

        boolean hasKw = keyword != null && !keyword.trim().isEmpty();

        String sql =
                "SELECT COUNT(*) FROM users " +
                        (hasKw ? "WHERE fullName LIKE :kw OR email LIKE :kw OR phoneNumber LIKE :kw " : "");

        final String finalSql = sql;
        final String finalKw = hasKw ? "%" + keyword.trim() + "%" : null;

        return jdbi.withHandle(h -> {
            var q = h.createQuery(finalSql);
            if (hasKw) q.bind("kw", finalKw);
            return q.mapTo(int.class).one();
        });
    }

    public int adminCreateUser(Users user) {
        String sql = """
    INSERT INTO users (fullName, email, password, phoneNumber, dob, address, role, createAt, isActive)
    VALUES (:fullName, :email, :password, :phoneNumber, :dob, :address, :role, CURRENT_TIMESTAMP(), :isActive)
""";


        return jdbi.withHandle(h -> h.createUpdate(sql)
                .bind("fullName", user.getFullName())
                .bind("email", user.getEmail())
                .bind("password", user.getPassword())
                .bind("phoneNumber", user.getPhoneNumber())
                .bind("dob", user.getDob() != null ? java.sql.Date.valueOf(user.getDob()) : null)
                .bind("address", user.getAddress())
                .bind("role", user.getRole())
                .bind("isActive", user.getIsActive())
                .execute());
    }

    public int adminUpdateUser(Users user) {
        String sql = """
        UPDATE users
        SET fullName = :fullName,
            phoneNumber = :phoneNumber,
            dob = :dob,
            address = :address,
            role = :role
        WHERE id = :id
    """;

        return jdbi.withHandle(h -> h.createUpdate(sql)
                .bind("fullName", user.getFullName())
                .bind("phoneNumber", user.getPhoneNumber())
                .bind("dob", user.getDob() != null ? java.sql.Date.valueOf(user.getDob()) : null)
                .bind("address", user.getAddress())
                .bind("role", user.getRole())
                .bind("id", user.getId())
                .execute());
    }


    public int setActive(int userId, int isActive) {
        String sql = "UPDATE users SET isActive = :isActive WHERE ID = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("isActive", isActive)
                        .bind("id", userId)
                        .execute()
        );
    }
    public int insertUserAndReturnId(Users user) {
        String sql = "INSERT INTO users (fullName, email, password, phoneNumber, address, role, createAt, isActive) " +
                "VALUES (:fullName, :email, :password, :phoneNumber, :address, :role, CURRENT_TIMESTAMP(), :isActive)";

        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("fullName", user.getFullName())
                        .bind("email", user.getEmail())
                        .bind("password", user.getPassword())
                        .bind("phoneNumber", user.getPhoneNumber())
                        .bind("address", user.getAddress())
                        .bind("role", user.getRole())
                        .bind("isActive", user.getIsActive())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

}