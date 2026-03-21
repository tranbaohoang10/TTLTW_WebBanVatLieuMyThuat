package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.Product_ReviewsDao;
import vn.edu.nlu.fit.mythuatshop.Model.Product;
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

    public boolean canReviewProduct(int id, int productID) {
        return product_reviewsDao.canReviewProduct(id, productID);
    }
}
