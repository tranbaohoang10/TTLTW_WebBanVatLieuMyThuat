package vn.edu.nlu.fit.mythuatshop.Model;

import java.time.LocalDateTime;

public class UserProductInteraction {
    private int id;
    private int userId;
    private int productId;
    private String actionType;
    private int score;
    private LocalDateTime createdAt;

    public UserProductInteraction() {
    }

    public UserProductInteraction(int userId, int productId, String actionType, int score) {
        this.userId = userId;
        this.productId = productId;
        this.actionType = actionType;
        this.score = score;
    }

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


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}