package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Product_Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Product_ReviewsDao {
    private final Jdbi jdbi;

    public Product_ReviewsDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }
    public List<Product_Review> findAll(){
    String sql="select id,userID,productID,rating,comment,createAt from product_reviews";
        return jdbi.withHandle(h -> h.createQuery(sql).mapToBean(Product_Review.class).list());
    }
    public double getAverageRating(int productID){
        String sql = "select AVG(rating) from product_reviews where productID = :productID";
        return jdbi.withHandle(h -> h.createQuery(sql).bind("productID",productID).mapTo(Double.class).findOne().orElse(0.0));
        }
    // Lấy danh sách review theo productID
    public List<Product_Review> findByProductId(int productId) {
        String sql =
                "SELECT pr.id, pr.userID, pr.productID, pr.rating, pr.comment, pr.createAt, " +
                        "u.fullName AS username " +
                        "FROM product_reviews pr " +
                        "JOIN users u ON pr.userID = u.id " +
                        "WHERE pr.productID = :pid " +
                        "ORDER BY pr.createAt DESC";

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("pid", productId)
                        .mapToBean(Product_Review.class)
                        .list()
        );
    }
    // Thêm 1 review mới
    public void insert(Product_Review review) {
        String sql = "INSERT INTO product_reviews(userID, productID, rating, comment, createAt) " +
                "VALUES (:userID, :productID, :rating, :comment, NOW())";

        jdbi.useHandle(h ->
                h.createUpdate(sql)
                        .bind("userID", review.getUserID())
                        .bind("productID", review.getProductID())
                        .bind("rating", review.getRating())
                        .bind("comment", review.getComment())
                        .execute()
        );
    }

    public boolean canReviewProduct(int userId, int productID) {
        String sql = """
            SELECT COUNT(*)
            FROM orders o
            JOIN order_details od ON o.ID = od.orderID
            JOIN order_statuses os ON os.ID = o.orderStatusID
            WHERE o.userID = :userID
              AND od.productID = :productID
              AND os.statusName = 'Hoàn thành'
            """;

        Integer count = jdbi.withHandle(h -> h.createQuery(sql)
                .bind("userID", userId)
                .bind("productID", productID)
                .mapTo(Integer.class)
                .one()
        );

        return count != null && count > 0;
    }

    public boolean hasUserReviewed(int userId, int productID) {
        String sql = """
                SELECT COUNT(*)
                FROM product_reviews pr
                WHERE pr.userId = :userId
                AND pr.productID = :productID
                """;
        Integer count = jdbi.withHandle(h -> h.createQuery(sql)
                .bind("userId",userId)
                .bind("productID",productID)
                .mapTo(Integer.class).one()
        );
        return count != null && count > 0;
    }
    public void update(Product_Review review) {
        String sql = """
            UPDATE product_reviews
            SET rating = :rating,
                comment = :comment
            WHERE userID = :userID
              AND productID = :productID
            """;

        jdbi.useHandle(h ->
                h.createUpdate(sql)
                        .bind("rating", review.getRating())
                        .bind("comment", review.getComment())
                        .bind("userID", review.getUserID())
                        .bind("productID", review.getProductID())
                        .execute()
        );
    }
}
