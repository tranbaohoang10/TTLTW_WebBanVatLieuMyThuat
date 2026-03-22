package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EmailVerificationTokenDao {
    private final Jdbi jdbi = JDBIConnector.getJdbi();

    public void insert(int userId, String token, LocalDateTime expiresAt) {
        String sql = "INSERT INTO email_verification_tokens(user_id, token, expires_at) " +
                "VALUES (:userId, :token, :expiresAt)";
        jdbi.useHandle(h -> h.createUpdate(sql)
                .bind("userId", userId)
                .bind("token", token)
                .bind("expiresAt", Timestamp.valueOf(expiresAt))
                .execute());
    }

    public Integer findUserIdIfValid(String token) {
        String sql = "SELECT user_id FROM email_verification_tokens " +
                "WHERE token = :token AND used_at IS NULL AND expires_at > NOW()";
        return jdbi.withHandle(h -> h.createQuery(sql)
                .bind("token", token)
                .mapTo(Integer.class)
                .findOne()
                .orElse(null));
    }

    public void markUsed(String token) {
        String sql = "UPDATE email_verification_tokens SET used_at = NOW() WHERE token = :token";
        jdbi.useHandle(h -> h.createUpdate(sql).bind("token", token).execute());
    }
    public String findValidTokenByUserId(int userId) {
        String sql = "SELECT token FROM email_verification_tokens " +
                "WHERE user_id = ? AND used_at IS NULL AND expires_at > NOW() LIMIT 1";
        String token = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, userId).mapTo(String.class).findOne().orElse(null)
        );
        return token;
    }
    public void deleteTokensByUserId(int userId) {
        String sql = "DELETE FROM email_verification_tokens WHERE user_id = ?";
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate(sql).bind(0, userId).execute();
        });
    }
}
