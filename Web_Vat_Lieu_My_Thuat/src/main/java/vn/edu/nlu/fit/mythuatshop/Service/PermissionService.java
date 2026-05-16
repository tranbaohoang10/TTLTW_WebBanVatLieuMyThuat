package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.PermissionDao;

import java.util.Set;

public class PermissionService {
    private final PermissionDao permissionDao = new PermissionDao();

    public Set<String> getPermissionsByUserId(int userId) {
        return permissionDao.getCodesByUserId(userId);
    }

    public boolean hasPermission(int userId, String permissionCode) {
        if (permissionCode == null) {
            return false;
        }

        Set<String> permissions = permissionDao.getCodesByUserId(userId);
        return permissions.contains(permissionCode);
    }
}