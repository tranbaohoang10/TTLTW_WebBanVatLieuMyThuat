package vn.edu.nlu.fit.mythuatshop.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        String ctx = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(ctx.length());


        boolean isPublic =
                path.equals("/") ||
                        path.startsWith("/home") ||
                        path.startsWith("/login") ||
                        path.startsWith("/logout") ||
                        path.startsWith("/register") ||
                        path.startsWith("/forgotpassword") ||
                        path.startsWith("/verify-email") ||
                        path.startsWith("/reset-password") ||
                        path.equals("/oauth2/google") ||
                        path.equals("/oauth2/callback/google") ||
                        path.startsWith("/assets/") ||
                        path.endsWith(".css") ||
                        path.endsWith(".js") ||
                        path.endsWith(".png") || path.endsWith(".jpg") ||
                        path.endsWith(".jpeg") || path.endsWith(".gif") ||
                        path.startsWith("/contact") ||
                        path.endsWith(".ico");
        HttpSession session = req.getSession(false);
        Object authUser = (session == null) ? null : session.getAttribute("currentUser");

        boolean hadSessionID = req.getRequestedSessionId() != null;
        boolean sessionIDValid = req.isRequestedSessionIdValid();
        boolean sessionExpired = hadSessionID && !sessionIDValid;
        if (uri.contains("/ghn/")) {
            chain.doFilter(request, response);
            return;
        }
        if (!isPublic && authUser == null) {
            if (sessionExpired) {
                resp.sendRedirect(ctx + "/login?timeout=1");
            } else {
                resp.sendRedirect(ctx + "/login");
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
