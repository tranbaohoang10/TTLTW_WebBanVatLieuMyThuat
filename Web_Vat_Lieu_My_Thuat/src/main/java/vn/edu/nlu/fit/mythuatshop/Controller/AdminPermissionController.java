package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.GroupRole;
import vn.edu.nlu.fit.mythuatshop.Service.GroupRoleService;
import vn.edu.nlu.fit.mythuatshop.Service.PermissionService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "AdminPermissionController", value = "/admin/permissions")
public class AdminPermissionController extends HttpServlet {
    private final GroupRoleService groupRoleService = new GroupRoleService();
    private final PermissionService permissionService = new PermissionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<GroupRole> groups = groupRoleService.getStaffGroups();

        int selectedGroupId = 0;
        String groupIdParam = request.getParameter("groupId");

        if (groupIdParam != null && !groupIdParam.isBlank()) {
            selectedGroupId = Integer.parseInt(groupIdParam);
        } else if (!groups.isEmpty()) {
            selectedGroupId = groups.get(0).getId();
        }

        GroupRole selectedGroup = null;
        for (GroupRole group : groups) {
            if (group.getId() == selectedGroupId) {
                selectedGroup = group;
                break;
            }
        }

        Set<String> groupPermissions = permissionService.getPermissionsByGroupId(selectedGroupId);

        request.setAttribute("groups", groups);
        request.setAttribute("selectedGroup", selectedGroup);
        request.setAttribute("selectedGroupId", selectedGroupId);
        request.setAttribute("groupPermissions", groupPermissions);
        request.setAttribute("msg", request.getParameter("msg"));

        request.getRequestDispatcher("/admin/Permission.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String[] permissions = request.getParameterValues("permissions");

        permissionService.updateGroupPermissions(groupId, permissions);

        response.sendRedirect(request.getContextPath()
                + "/admin/permissions?groupId=" + groupId + "&msg=success");
    }
}