package vn.edu.nlu.fit.mythuatshop.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Controller.AdminResource;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;
import vn.edu.nlu.fit.mythuatshop.Util.PermissionUtil;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Set;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthFilter implements Filter {
    private UserService userService;

    public void init(FilterConfig config) throws ServletException {
        userService = new UserService();
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Users currentUser = null;

        if(session != null){
            currentUser = (Users) session.getAttribute("currentUser");
        }

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Users latestUser = userService.getUserById(currentUser.getId());
        if (latestUser == null) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Long loginTime = (Long) session.getAttribute("loginTime");
        if(loginTime != null && latestUser.getPermissionUpdateAt() != null) {
            long permissionTime = latestUser.getPermissionUpdateAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if(permissionTime > loginTime) {
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login?permissionChanged=1");
                return;
            }
        }
        if(loginTime != null && latestUser.getStatusUpdateAt() != null) {
            long statusTime = latestUser.getStatusUpdateAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if(statusTime > loginTime) {
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login?statusChanged=1");
                return;
            }
        }

        String role = currentUser.getRole();
            if(role == null || role.equalsIgnoreCase("USER")){
                PermissionUtil.showNoPermission(req, resp);
                return;
            }
            String path = req.getServletPath() ;
            if ("/admin/orders/status".equals(path)) {
                chain.doFilter(request, response);
                return;
            }
            String permissionCode = AdminResource.getPermissionCode(path);
            if(permissionCode == null){
                PermissionUtil.show404(req, resp);
                return;
            }
            if(role.equalsIgnoreCase("ADMIN")){
                chain.doFilter(request, response);
                return;
            }
            if (role.equalsIgnoreCase("STAFF")) {
                Set<String> permissions = (Set<String>) session.getAttribute("permissions");
             if (permissions != null && permissions.contains(permissionCode)) {
                chain.doFilter(request, response);
                return;
            }

                PermissionUtil.showNoPermission(req, resp);
            return;
        }

        PermissionUtil.showNoPermission(req, resp);
    }

}