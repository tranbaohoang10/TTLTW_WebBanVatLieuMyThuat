package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.Product_ReviewsDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product_Review;

import java.util.List;

public class Product_ReviewService {
    private Product_ReviewsDao product_reviewsDao;

    public Product_ReviewService() {
        this.product_reviewsDao = new Product_ReviewsDao();
    }
    public List<Product_Review> productReviewList(){
        return product_reviewsDao.findAll();
    }
    public double averageRating(int productID){
        return product_reviewsDao.getAverageRating(productID);
    }
    // Lấy list review theo productID
    public List<Product_Review> getReviewsByProductId(int productId) {
        return product_reviewsDao.findByProductId(productId);
    }

    // Thêm 1 review mới
    public void addReview(Product_Review review) {
        product_reviewsDao.insert(review);
    }

    public boolean canReviewProduct(int userId, int productID) {
        return product_reviewsDao.canReviewProduct(userId, productID);
    }

    public boolean hasReviewProduct(int userId, int productID) {
        return product_reviewsDao.hasUserReviewed(userId, productID);
    }
    public void updateReview(Product_Review review) {
        product_reviewsDao.update(review);
    }

    public boolean canReviewOrderProduct(int userId, int productID, int orderID) {
        return product_reviewsDao.canReviewOrderProduct(userId, productID, orderID);
    }

    public boolean hasReviewOrderProduct(int userId, int productID, int orderID) {
        return product_reviewsDao.hasUserReviewedOrderProduct(userId, productID, orderID);
    }

    public void updateReviewByOrderProduct(Product_Review review) {
        product_reviewsDao.updateByOrderProduct(review);
    }
}
