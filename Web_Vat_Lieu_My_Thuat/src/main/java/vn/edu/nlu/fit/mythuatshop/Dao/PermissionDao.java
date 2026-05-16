package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermissionDao {
    private final Jdbi jdbi;

    public PermissionDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public Set<String> getCodesByUserId(int userId) {
        String sql = "SELECT DISTINCT p.code " +
                "FROM users u " +
                "JOIN group_permissions gp ON u.group_id = gp.group_id " +
                "JOIN permissions p ON gp.permission_code = p.code " +
                "WHERE u.id = :userId";

        List<String> permissions = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("userId", userId)
                        .mapTo(String.class)
                        .list()
        );

        return new HashSet<>(permissions);
    }
}