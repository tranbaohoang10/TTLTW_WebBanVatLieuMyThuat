package vn.edu.nlu.fit.mythuatshop.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.AdminUserService;
import vn.edu.nlu.fit.mythuatshop.Service.LogService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "AdminUsersController", value = "/admin/users")
public class AdminUserController extends HttpServlet {

    private final AdminUserService adminUserService = new AdminUserService();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final LogService logService = new LogService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        String q = request.getParameter("q");
        String msg = request.getParameter("msg");


        if (q == null) {
            q = "";
        }
        try{
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (Exception e) {
            page = 1;
        }

        List<Users> users = adminUserService.listUsers(page, pageSize, q);
        int totalPages = adminUserService.totalPages(pageSize, q);

        String ajax = request.getParameter("ajax");
        String requestedWith = request.getHeader("X-Requested-With");


        if ("1".equals(ajax) || "XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            List<Map<String, Object>> userRows = new ArrayList<>();
            for (Users user : users) {
                userRows.add(toUserMap(user));
            }
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("users", userRows);
            data.put("currentPage", page);
            data.put("totalPages", totalPages);
            data.put("q", q);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(gson.toJson(data));
            return;
        }


        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("q",q);
        request.setAttribute("msg", msg);

        request.getRequestDispatcher("/admin/User.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String page = request.getParameter("page");
        String q = request.getParameter("q");

        if (page == null || page.isBlank()) {
            page = "1";
        }
        if (q == null) {
            q = "";
        }
        boolean result = false;

        if ("create".equals(action)) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String role = request.getParameter("role");

            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();

            result = adminUserService.createUser(fullName, email, phoneNumber, dob, address, role, baseUrl);
            if (result) {
                Users after = new Users();
                after.setFullName(fullName);
                after.setEmail(email);
                after.setPhoneNumber(phoneNumber);
                after.setAddress(address);
                after.setRole(role);
                writeLog(request, "Tạo người dùng", "AdminUserController#create", null, after);
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String role = request.getParameter("role");

            Users before = adminUserService.getUserById(id);
            result = adminUserService.updateUser(id, fullName, phoneNumber, dob, address, role);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Cập nhật người dùng", "AdminUserController#update", before, after);
            }

        } else if ("lock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users before = adminUserService.getUserById(id);
            result = adminUserService.lockUser(id);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Khóa người dùng", "AdminUserController#lock", before, after);
            }

        } else if ("unlock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users before = adminUserService.getUserById(id);
            result = adminUserService.unlockUser(id);
            if (result) {
                Users after = adminUserService.getUserById(id);
                writeLog(request, "Mở khóa người dùng", "AdminUserController#unlock", before, after);
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
        String qEncoded = URLEncoder.encode(q, StandardCharsets.UTF_8);
        response.sendRedirect(
                request.getContextPath() + "/admin/users?page=" + page + "&q=" + qEncoded + "&msg=" + msg
        );
    }
    private Map<String, Object> toUserMap(Users user) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", user.getId());
        row.put("fullName", user.getFullName());
        row.put("phoneNumber", user.getPhoneNumber());
        row.put("address", user.getAddress());
        row.put("createAt", user.getCreateAt() == null ? "" : user.getCreateAt().toString());
        row.put("dob", user.getDob() == null ? "" : user.getDob().toString());
        row.put("role", user.getRole());
        row.put("isActive", user.getIsActive());
        return row;
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