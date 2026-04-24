package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Log;

import java.util.List;

public class LogDao {
    private final Jdbi jdbi = JDBIConnector.getJdbi();

    public void insert(Log log) {
        String sql = "INSERT INTO logs(label, user_id, time, location, before_data, after_data) " +
                "VALUES (:label, :userId, :time, :location, :beforeData, :afterData)";
        jdbi.useHandle(handle -> handle.createUpdate(sql).bind("label", log.getLabel())
                .bind("userId", log.getUserId())
                .bind("time", log.getTime())
                .bind("location", log.getLocation())
                .bind("beforeData", log.getBeforeData())
                .bind("afterData", log.getAfterData()).execute());
    }
    public List<Log> getAll() {
        String sql = "SELECT l.*, u.fullName AS userName FROM logs l LEFT JOIN users u ON l.user_id = u.id ORDER BY l.time DESC" ;

        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .map((rs, ctx) -> {
                    Log log = new Log();
                    log.setId(rs.getInt("id"));
                    log.setLabel(rs.getString("label"));
                    log.setUserId(rs.getInt("user_id"));
                    log.setTime(rs.getTimestamp("time"));
                    log.setLocation(rs.getString("location"));
                    log.setBeforeData(rs.getString("before_data"));
                    log.setAfterData(rs.getString("after_data"));
                    log.setUserName(rs.getString("userName"));

                    return log;
                })
                .list());
    }
    public Log getById(int id) {
        String sql = "SELECT l.*, u.fullName AS userName " +
                "FROM logs l " +
                "LEFT JOIN users u ON l.user_id = u.id " +
                "WHERE l.id = :id";

        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .map((rs, ctx) -> {
                    Log log = new Log();

                    log.setId(rs.getInt("id"));
                    log.setLabel(rs.getString("label"));
                    log.setUserId(rs.getInt("user_id"));
                    log.setTime(rs.getTimestamp("time"));
                    log.setLocation(rs.getString("location"));
                    log.setBeforeData(rs.getString("before_data"));
                    log.setAfterData(rs.getString("after_data"));
                    log.setUserName(rs.getString("userName"));

                    return log;
                })
                .findOne()
                .orElse(null));
    }
}
