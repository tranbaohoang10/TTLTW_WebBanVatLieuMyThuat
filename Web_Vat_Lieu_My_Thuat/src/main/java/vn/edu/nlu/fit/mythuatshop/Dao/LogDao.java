package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Log;

public class LogDao {
    private final Jdbi jdbi = JDBIConnector.getJdbi();

    public void insert(Log log) {
        String sql = "INSERT INTO logs(label, user_id, time, location, before_data, after_data) " +
                "VALUES (:label, :userId, :time, :location, :beforeData, :afterData)";
        jdbi.useHandle(handle -> handle.createUpdate(sql).bind("label", log.getLabel())
                .bind("user_id", log.getId())
                .bind("time", log.getTime())
                .bind("location", log.getLocation())
                .bind("beforeData", log.getBeforeData())
                .bind("afterData", log.getAfterData()).execute());
    }
}
