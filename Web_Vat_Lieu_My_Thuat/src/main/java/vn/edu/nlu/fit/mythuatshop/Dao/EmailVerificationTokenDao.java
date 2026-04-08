package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EmailVerificationTokenDao {
    private final Jdbi jdbi = JDBIConnector.getJdbi();

    public void insert(int userId, String token, LocalDateTime expiresAt, String type) {
        String sql = "INSERT INTO email_verification_tokens(user_id, token, expires_at, type) " +
                "VALUES (:userId, :token, :expiresAt, :type)";
        jdbi.useHandle(h -> h.createUpdate(sql)
                .bind("userId", userId)
                .bind("token", token)
                .bind("expiresAt", Timestamp.valueOf(expiresAt))
                .bind("type", type)
                .execute());
    }

    public Integer findUserIdIfValid(String token, String type) {
        String sql = "SELECT user_id FROM email_verification_tokens " +
                "WHERE token = :token AND type = :type AND used_at IS NULL AND expires_at > NOW()";
        return jdbi.withHandle(h -> h.createQuery(sql)
                .bind("token", token)
                .bind("type", type)
                .mapTo(Integer.class)
                .findOne()
                .orElse(null));
    }

    public void markUsed(String token) {
        String sql = "UPDATE email_verification_tokens SET used_at = NOW() WHERE token = :token";
        jdbi.useHandle(h -> h.createUpdate(sql).bind("token", token).execute());
    }
    public String findValidTokenByUserId(int userId, String type) {
        String sql = "SELECT token FROM email_verification_tokens " +
                "WHERE user_id = :userId AND type = :type AND used_at IS NULL AND expires_at > NOW() LIMIT 1";
        String token = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, userId).bind("type", type).mapTo(String.class).findOne().orElse(null)
        );
        return token;
    }
    public void deleteTokensByUserId(int userId, String type) {
        String sql = "DELETE FROM email_verification_tokens WHERE user_id = :userId AND type = :type";
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate(sql).bind("userId", userId).bind("type", type).execute();
        });
    }
}
