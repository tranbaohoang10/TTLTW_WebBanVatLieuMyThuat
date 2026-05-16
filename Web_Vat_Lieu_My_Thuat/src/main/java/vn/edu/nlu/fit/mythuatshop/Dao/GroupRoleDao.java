package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.GroupRole;

import java.util.List;

public class GroupRoleDao {
    private final Jdbi jdbi;
    public GroupRoleDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }
    public List<GroupRole> findAll() {
        String sql = "select id, code, name " +
                "from groups_role " +
                "where code in ('ADMIN_GROUP', 'ORDER_STAFF_GROUP', 'CONTENT_STAFF_GROUP', 'SUPPORT_STAFF_GROUP') " +
                "order by id";
        return  jdbi.withHandle(handle -> handle.createQuery(sql).mapToBean(GroupRole.class).list());
    }
}
