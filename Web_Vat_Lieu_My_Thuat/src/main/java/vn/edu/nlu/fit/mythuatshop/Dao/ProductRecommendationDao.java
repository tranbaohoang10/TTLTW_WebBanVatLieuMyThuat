package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
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
}