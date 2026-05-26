package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;

public class UserProductInteractionDao {
    private final Jdbi jdbi;

    public UserProductInteractionDao() {
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
}