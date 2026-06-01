package vn.edu.nlu.fit.mythuatshop.Model;

public class ProductRecommendation {
    private int userId;
    private int productId;
    private double predictedScore;

    public ProductRecommendation() {
    }

    public ProductRecommendation(int userId, int productId, double predictedScore) {
        this.userId = userId;
        this.productId = productId;
        this.predictedScore = predictedScore;
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

    public double getPredictedScore() {
        return predictedScore;
    }

    public void setPredictedScore(double predictedScore) {
        this.predictedScore = predictedScore;
    }
}