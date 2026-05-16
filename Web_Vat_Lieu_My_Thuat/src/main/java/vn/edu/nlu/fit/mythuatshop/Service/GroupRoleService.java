package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.GroupRoleDao;
import vn.edu.nlu.fit.mythuatshop.Model.GroupRole;

import java.util.List;

public class GroupRoleService {
    private final GroupRoleDao groupRoleDao = new GroupRoleDao();
    public List<GroupRole> getAllGroup() {
        return groupRoleDao.findAll();
    }
}
