package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.ProductTrainData;

import java.util.List;

public class ProductInteractionDao {
    private final Jdbi jdbi;

    public ProductInteractionDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public void insert(int userId, int productId, String actionType, int score) {
        String sql = """
                INSERT INTO user_product_interactions(userID, productID, actionType, score)
                VALUES (:userId, :productId, :actionType, :score)
                """;

        jdbi.useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("userId", userId)
                        .bind("productId", productId)
                        .bind("actionType", actionType)
                        .bind("score", score)
                        .execute()
        );
    }
    public List<ProductTrainData> getTrainingData() {
        String sql = """
            SELECT 
                userID AS userId,
                productID AS productId,
                SUM(score) AS score
            FROM user_product_interactions
            GROUP BY userID, productID
            ORDER BY userID, score DESC
            """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(ProductTrainData.class)
                        .list()
        );
    }
    public void insertPurchaseBatch(int userId, List<Integer> productIds) {
        String sql = "INSERT INTO user_product_interactions(userID, productID, actionType, score) " +
                "VALUES (:userId, :productId, :actionType, :score)";
        jdbi.useHandle(handle -> {
            var batch = handle.prepareBatch(sql);
            for (Integer productId : productIds) {
                batch.bind("userId", userId)
                        .bind("productId", productId)
                        .bind("actionType", "PURCHASE")
                        .bind("score", 10)
                        .add();
            }
            batch.execute();
        });
    }
}