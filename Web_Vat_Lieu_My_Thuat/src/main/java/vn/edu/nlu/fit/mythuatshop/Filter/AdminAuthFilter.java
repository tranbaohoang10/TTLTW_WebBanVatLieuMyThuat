package vn.edu.nlu.fit.mythuatshop.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Users;

import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
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
        String role = currentUser.getRole();
            if(role == null){
                show404(req, resp);
                return;
            }
            String path = req.getServletPath() ;
            if(role.equalsIgnoreCase("ADMIN")){
                chain.doFilter(request, response);
                return;
            }
            if (role.equalsIgnoreCase("STAFF")) {
             if (isStaffPage(path)) {
                chain.doFilter(request, response);
                return;
            }

            show404(req, resp);
            return;
        }

        show404(req, resp);
    }
    private void show404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        req.getRequestDispatcher("/Error404.jsp").forward(req, resp);
    }
    private boolean isStaffPage(String path) {
        return path.equals("/admin/overview")
                || path.equals("/admin/products")
                || path.equals("/admin/orders")
                || path.equals("/admin/order-detail")
                || path.equals("/admin/orders/edit")
                || path.equals("/admin/orders/status")
                || path.equals("/admin/contacts")
                || path.equals("/admin/contacts/delete")
                || path.equals("/admin/contacts/reply");
    }
}