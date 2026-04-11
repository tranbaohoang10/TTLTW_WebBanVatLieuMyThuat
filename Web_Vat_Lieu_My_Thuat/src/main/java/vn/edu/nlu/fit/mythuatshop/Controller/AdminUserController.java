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

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminUsersController", value = "/admin/users")
public class AdminUserController extends HttpServlet {

    private final AdminUserService adminUserService = new AdminUserService();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    static class UserRowDto {
        int id;
        String fullName;
        String phoneNumber;
        String address;
        String createAt;
        String dob;
        String role;
        int isActive;

        public static UserRowDto fromUser(Users user) {
            UserRowDto dto = new UserRowDto();
            dto.id = user.getId();
            dto.fullName = user.getFullName();
            dto.phoneNumber = user.getPhoneNumber();
            dto.address = user.getAddress();
            dto.createAt = user.getCreateAt() == null ? "" : user.getCreateAt().toString();
            dto.dob = user.getDob() == null ? "" : user.getDob().toString();
            dto.role = user.getRole();
            dto.isActive = user.getIsActive();
            return dto;
        }
    }

    static class AjaxResponse {
        List<UserRowDto> users = new ArrayList<>();
        int currentPage;
        int totalPages;
        String q;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (Exception e) {
                page = 1;
            }
        }

        String q = request.getParameter("q");
        String msg = request.getParameter("msg");

        boolean isAjax = false;
        String ajaxParam = request.getParameter("ajax");
        String requestedWith = request.getHeader("X-Requested-With");

        if ("1".equals(ajaxParam) || "XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            isAjax = true;
        }

        if (isAjax) {
            List<Users> userList = adminUserService.listUsers(page, pageSize, q);
            int totalPages = adminUserService.totalPages(pageSize, q);

            AjaxResponse ajaxResponse = new AjaxResponse();
            for (Users user : userList) {
                ajaxResponse.users.add(UserRowDto.fromUser(user));
            }
            ajaxResponse.currentPage = page;
            ajaxResponse.totalPages = totalPages;
            ajaxResponse.q = q == null ? "" : q;

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(gson.toJson(ajaxResponse));
            return;
        }

        List<Users> users = adminUserService.listUsers(page, pageSize, q);
        int totalPages = adminUserService.totalPages(pageSize, q);

        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("q", q == null ? "" : q);
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

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String role = request.getParameter("role");

            result = adminUserService.updateUser(id, fullName, phoneNumber, dob, address, role);

        } else if ("lock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            result = adminUserService.lockUser(id);

        } else if ("unlock".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            result = adminUserService.unlockUser(id);
        }

        String msg = result ? "success" : "fail";
        String qEncoded = URLEncoder.encode(q, StandardCharsets.UTF_8);

        response.sendRedirect(
                request.getContextPath() + "/admin/users?page=" + page + "&q=" + qEncoded + "&msg=" + msg
        );
    }
}