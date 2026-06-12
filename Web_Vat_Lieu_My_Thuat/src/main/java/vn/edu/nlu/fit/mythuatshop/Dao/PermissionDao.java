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
    public Set<String> getCodesByGroupId(int groupId) {
        String sql = "SELECT permission_code " +
                "FROM group_permissions " +
                "WHERE group_id = :groupId";

        List<String> permissions = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("groupId", groupId)
                        .mapTo(String.class)
                        .list()
        );

        return new HashSet<>(permissions);
    }
    public void updateGroupPermissions(int groupId, String[] permissionCodes) {
        jdbi.useTransaction(handle -> {
            handle.createUpdate("DELETE FROM group_permissions WHERE group_id = :groupId")
                    .bind("groupId", groupId)
                    .execute();

            if (permissionCodes != null) {
                for (String code : permissionCodes) {
                    handle.createUpdate("INSERT INTO group_permissions(group_id, permission_code) " +
                                    "VALUES (:groupId, :permissionCode)")
                            .bind("groupId", groupId)
                            .bind("permissionCode", code)
                            .execute();
                }
            }

            handle.createUpdate("UPDATE users " +
                            "SET permission_updated_at = NOW() " +
                            "WHERE group_id = :groupId")
                    .bind("groupId", groupId)
                    .execute();
        });
    }
}