package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.SliderShow;

import java.util.List;
import java.util.Optional;

public class SliderShowDao {
    private final Jdbi jdbi;

    public SliderShowDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public List<SliderShow> getSliderShowAll() {
        String sql = """
            SELECT id, title, thumbnail, status, indexOrder, linkTo
            FROM slidershows
            WHERE status = 1
            ORDER BY indexOrder ASC, id DESC
        """;
        return jdbi.withHandle(h -> h.createQuery(sql).mapToBean(SliderShow.class).list());
    }

    public int countByKeyword(String keyword) {
        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();
        String sql = """
            SELECT COUNT(*)
            FROM slidershows
            WHERE (:kw IS NULL OR title LIKE :kwLike OR linkTo LIKE :kwLike)
        """;
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("kw", kw)
                        .bind("kwLike", kw == null ? null : "%" + kw + "%")
                        .mapTo(Integer.class)
                        .one()
        );
    }


    public List<SliderShow> findPageByKeyword(String keyword, int offset, int limit) {
        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();
        String sql = """
            SELECT id, title, thumbnail, status, indexOrder, linkTo
            FROM slidershows
            WHERE (:kw IS NULL OR title LIKE :kwLike OR linkTo LIKE :kwLike)
            ORDER BY indexOrder ASC, id DESC
            LIMIT :limit OFFSET :offset
        """;
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("kw", kw)
                        .bind("kwLike", kw == null ? null : "%" + kw + "%")
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(SliderShow.class)
                        .list()
        );
    }

    public Optional<SliderShow> findById(int id) {
        String sql = """
            SELECT id, title, thumbnail, status, indexOrder, linkTo
            FROM slidershows
            WHERE id = :id
        """;
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(SliderShow.class)
                        .findOne()
        );
    }

    public boolean existsIndexOrder(int indexOrder, Integer excludeId) {
        String sql = """
            SELECT COUNT(*)
            FROM slidershows
            WHERE indexOrder = :idx
              AND (:excludeId IS NULL OR id <> :excludeId)
        """;
        int c = jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("idx", indexOrder)
                        .bind("excludeId", excludeId)
                        .mapTo(Integer.class)
                        .one()
        );
        return c > 0;
    }

    public int insert(SliderShow s) {
        String sql = """
            INSERT INTO slidershows(title, thumbnail, status, indexOrder, linkTo)
            VALUES (:title, :thumbnail, :status, :indexOrder, :linkTo)
        """;
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bindBean(s)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int update(SliderShow s) {
        String sql = """
            UPDATE slidershows
            SET title = :title,
                thumbnail = :thumbnail,
                status = :status,
                indexOrder = :indexOrder,
                linkTo = :linkTo
            WHERE id = :id
        """;
        return jdbi.withHandle(h -> h.createUpdate(sql).bindBean(s).execute());
    }

    public int updateStatus(int id, int status) {
        String sql = "UPDATE slidershows SET status = :status WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", id)
                        .bind("status", status)
                        .execute()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM slidershows WHERE id = :id";
        return jdbi.withHandle(h -> h.createUpdate(sql).bind("id", id).execute());
    }
}
