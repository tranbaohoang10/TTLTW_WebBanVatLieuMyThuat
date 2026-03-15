package vn.edu.nlu.fit.mythuatshop.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.UserService;
import vn.edu.nlu.fit.mythuatshop.Util.Env;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "GoogleCallbackController", value = "/oauth2/callback/google")
public class GoogleCallbackController extends HttpServlet {

    private static final String CLIENT_ID = Env.require("GOOGLE_CLIENT_ID");
    private static final String CLIENT_SECRET = Env.require("GOOGLE_CLIENT_SECRET");
    private static final String REDIRECT_URI = Env.require("GOOGLE_REDIRECT_URI");

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String maTTTraVe = req.getParameter("state");

        HttpSession session = req.getSession();
        String savedState = (String) session.getAttribute("maTTInSession");
        session.removeAttribute("maTTInSession");

        if (code == null || savedState == null || !savedState.equals(maTTTraVe)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        JsonObject tokenJson = exchangeCodeForToken(code);
        String accessToken = tokenJson.get("access_token").getAsString();

        JsonObject userInfo = fetchUserInfo(accessToken);
        String email = userInfo.get("email").getAsString();
        String fullName = userInfo.has("name") ? userInfo.get("name").getAsString() : "";


        Users user = userService.findByEmailForGG(email);
        if (user == null) {
            user = userService.registerGoogleUser(fullName, email);
        }
        if (user != null && user.getIsActive() == 3) {
            session.setAttribute("FLASH_ERROR", "Tài khoản đã bị khóa!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        session.setAttribute("currentUser", user);
        session.setMaxInactiveInterval(30 * 60);

        Object obj = session.getAttribute("cart");
        Cart cart;
        if (obj instanceof Cart) {
            cart = (Cart) obj;
        } else {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        session.setAttribute("cartCount", cart.getTotalQuantity());

        String role = user.getRole();
        if (role != null && role.equalsIgnoreCase("ADMIN")) {
            resp.sendRedirect(req.getContextPath() + "/admin/overview");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/home");
    }

    private JsonObject exchangeCodeForToken(String code) throws IOException {
        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String body = "code=" + enc(code) + "&client_id=" + enc(CLIENT_ID) + "&client_secret=" + enc(CLIENT_SECRET )
                + "&redirect_uri=" + enc(REDIRECT_URI) + "&grant_type=authorization_code";

        try (OutputStream os = con.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        String json = readAll(con);
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private JsonObject fetchUserInfo(String accessToken) throws IOException {
        URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);

        String json = readAll(con);
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private static String readAll(HttpURLConnection con) throws IOException {
        InputStream is = (con.getResponseCode() >= 200 && con.getResponseCode() < 300)
                ? con.getInputStream()
                : con.getErrorStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }

    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
