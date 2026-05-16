package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Category;

import java.util.List;

public class CategoryDao {
    private final Jdbi jdbi;

    public CategoryDao() {
        jdbi = JDBIConnector.getJdbi();
    }

    public List<Category> findAll() {
        String sql = "SELECT id, categoryName, thumbnail, isActive " +
                "FROM categories " +
                "ORDER BY id DESC";
        return jdbi.withHandle(h ->
                h.createQuery(sql).mapToBean(Category.class).list()
        );
    }

    public List<Category> findAllActive() {
        String sql = "SELECT id, categoryName, thumbnail, isActive " +
                "FROM categories " +
                "WHERE isActive = 1 " +
                "ORDER BY id DESC";
        return jdbi.withHandle(h ->
                h.createQuery(sql).mapToBean(Category.class).list()
        );
    }

    public Category findById(int id) {
        String sql = "SELECT id, categoryName, thumbnail, isActive " +
                "FROM categories " +
                "WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Category.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Category findByIdActive(int id) {
        String sql = "SELECT id, categoryName, thumbnail, isActive " +
                "FROM categories " +
                "WHERE id = :id AND isActive = 1";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Category.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public int insertReturnId(Category c) {
        String sql = """
            INSERT INTO categories(categoryName, thumbnail, isActive)
            VALUES (:categoryName, :thumbnail, :isActive)
        """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("categoryName", c.getCategoryName())
                        .bind("thumbnail", c.getThumbnail())
                        .bind("isActive", c.getIsActive())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int update(Category c) {
        String sql = """
            UPDATE categories
            SET categoryName = :categoryName,
                thumbnail = :thumbnail
            WHERE id = :id
        """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", c.getId())
                        .bind("categoryName", c.getCategoryName())
                        .bind("thumbnail", c.getThumbnail())
                        .execute()
        );
    }

    public int updateActive(int id, int isActive) {
        String sql = "UPDATE categories SET isActive = :isActive WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", id)
                        .bind("isActive", isActive)
                        .execute()
        );
    }
}
