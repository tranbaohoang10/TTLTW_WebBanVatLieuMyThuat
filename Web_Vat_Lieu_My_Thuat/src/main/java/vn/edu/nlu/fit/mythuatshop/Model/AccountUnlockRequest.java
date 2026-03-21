package vn.edu.nlu.fit.mythuatshop.Model;

import java.time.LocalDateTime;

public class AccountUnlockRequest {
    private int id;
    private int userId;
    private String email;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime handleAt;

    public AccountUnlockRequest(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getHandleAt() {
        return handleAt;
    }

    public void setHandleAt(LocalDateTime handleAt) {
        this.handleAt = handleAt;
    }
}
