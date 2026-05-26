package vn.edu.nlu.fit.mythuatshop.Model;

public class ProductTrainData {
    private int userId;
    private int productId;
    private int score;

    public ProductTrainData() {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}