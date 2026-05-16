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
        String sql = "select id, code, " + "case "
                                        + "when code = 'ADMIN_GROUP' then 'Nhóm quản trị' "+
                                           " when code = 'STAFF_GROUP' then 'Nhóm nhân viên'" +
                                           " when code = 'USER_GROUP' then 'Nhóm khách hàng' " +
                                           "else code" +
                                           " end as name" +
                                           " from groups_role" +
                                            " where code in ('ADMIN_GROUP', 'STAFF_GROUP', 'USER_GROUP')" +
                                           " order by id";
        return  jdbi.withHandle(handle -> handle.createQuery(sql).mapToBean(GroupRole.class).list());
    }
}
