package vn.edu.nlu.fit.mythuatshop.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.GroupRole;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.AdminUserService;
import vn.edu.nlu.fit.mythuatshop.Service.GroupRoleService;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;

import java.io.IOException;

import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "AdminUsersController", value = "/admin/users")
public class AdminUserController extends HttpServlet {

    private final AdminUserService adminUserService = new AdminUserService();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final LogService logService = new LogService();
    private final GroupRoleService groupRoleService = new GroupRoleService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = request.getParameter("msg");
        List<Users> users = adminUserService.listAllUsers();
        List<GroupRole> groups = groupRoleService.getAllGroup();

        request.setAttribute("users", users);
        request.setAttribute("groups", groups);

        request.setAttribute("msg", msg);

        request.getRequestDispatcher("/admin/User.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        boolean result = false;

        if ("create".equals(action)) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String role = request.getParameter("role");
            Integer groupId = getIntParam(request, "groupId");

            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();

            result = adminUserService.createUser(fullName, email, phoneNumber, dob, address, role, groupId, baseUrl);
            if (result) {
                Users after = new Users();
                after.setFullName(fullName);
                after.setEmail(email);
                after.setPhoneNumber(phoneNumber);
                after.setAddress(address);
                after.setRole(role);
                after.setGroupId(groupId);
                writeLog(request, "Tạo người dùng", "Quản lý người dùng", null, after);
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String role = request.getParameter("role");
            Integer groupId = getIntParam(request, "groupId");

            Users before = adminUserService.getUserById(id);
            result = adminUserService.updateUser(id, fullName, phoneNumber, dob, address, role, groupId);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Cập nhật người dùng", "Quản lý người dùng", before, after);
            }

        } else if ("lock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users before = adminUserService.getUserById(id);
            result = adminUserService.lockUser(id);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Khóa người dùng", "Quản lý người dùng", before, after);
            }

        } else if ("unlock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users before = adminUserService.getUserById(id);
            result = adminUserService.unlockUser(id);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Mở khóa người dùng", "Quản lý người dùng", before, after);
            }
        }

        String msg;
        if (result) {
            if ("create".equals(action)) {
                msg = "created_and_sent_mail";
            } else {
                msg = "success";
            }
        } else {
            msg = "fail";
        }
        response.sendRedirect(request.getContextPath() + "/admin/users?msg=" + msg);
    }

    private Integer getIntParam(HttpServletRequest request, String name) {
        try {
            String value = request.getParameter(name);
            if(value == null || value.isBlank()) {
                return null;
            }
            return Integer.parseInt(value);
        }catch (Exception e) {
            return null;
        }
    }
    private Integer getCurrentUserId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("currentUser");
        if (obj instanceof Users) {
            return ((Users) obj).getId();
        }
        return null;
    }

    private void writeLog(HttpServletRequest request, String label, String location, Object beforeData, Object afterData) {
        Integer userId = getCurrentUserId(request);
        if (userId != null) {
            logService.log(label, userId, location, beforeData, afterData);
        }
    }
}