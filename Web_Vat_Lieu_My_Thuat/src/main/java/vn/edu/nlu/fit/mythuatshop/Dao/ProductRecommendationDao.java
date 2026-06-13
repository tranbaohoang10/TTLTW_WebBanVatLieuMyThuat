package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;
import vn.edu.nlu.fit.mythuatshop.Model.ProductRecommendation;

import java.util.List;

public class ProductRecommendationDao {
    private final Jdbi jdbi;

    public ProductRecommendationDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public void deleteAll() {
        String sql = "DELETE FROM product_recommendations";

        jdbi.useHandle(handle ->
                handle.createUpdate(sql).execute()
        );
    }

    public void insertBatch(List<ProductRecommendation> list) {
        String sql = """
                INSERT INTO product_recommendations(userID, productID, predictedScore)
                VALUES (:userId, :productId, :predictedScore)
                """;

        jdbi.useHandle(handle -> {
            var batch = handle.prepareBatch(sql);

            for (ProductRecommendation item : list) {
                batch.bind("userId", item.getUserId())
                        .bind("productId", item.getProductId())
                        .bind("predictedScore", item.getPredictedScore())
                        .add();
            }

            batch.execute();
        });
    }
    public List<ProductCard> getRecommendedProducts(int userId, int limit) {
        String sql = "SELECT p.id, p.name, p.price, p.discountDefault, p.thumbnail, " +
                "p.quantityStock, p.soldQuantity, p.brand " +
                "FROM product_recommendations r " +
                "JOIN products p ON p.id = r.productID " +
                "WHERE r.userID = :userId AND p.isActive = 1 " +
                "ORDER BY r.predictedScore DESC " +
                "LIMIT :limit";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("userId", userId)
                        .bind("limit", limit)
                        .mapToBean(ProductCard.class)
                        .list()
        );
    }

    public List<ProductCard> getDefaultProducts(int limit) {
        String sql = "SELECT id, name, price, discountDefault, thumbnail, " +
                "quantityStock, soldQuantity, brand " +
                "FROM products " +
                "WHERE isActive = 1 " +
                "ORDER BY soldQuantity DESC, createAt DESC " +
                "LIMIT :limit";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("limit", limit)
                        .mapToBean(ProductCard.class)
                        .list()
        );
    }
    public int countRecommendations() {
        String sql = "SELECT COUNT(*) FROM product_recommendations";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public String getLastUpdatedTime() {
        String sql = "SELECT DATE_FORMAT(MAX(createdAt), '%d/%m/%Y %H:%i') " +
                "FROM product_recommendations";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(String.class)
                        .findOne()
                        .orElse("")
        );
    }
}