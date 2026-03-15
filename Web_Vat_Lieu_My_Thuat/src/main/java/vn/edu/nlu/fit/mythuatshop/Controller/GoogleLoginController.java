package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import vn.edu.nlu.fit.mythuatshop.Util.Env;


@WebServlet(name = "GoogleLoginController", value = "/oauth2/google")
public class GoogleLoginController extends HttpServlet {


    private static final String CLIENT_ID = Env.require("GOOGLE_CLIENT_ID");
    private static final String REDIRECT_URI = Env.require("GOOGLE_REDIRECT_URI");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        String maTT = UUID.randomUUID().toString();
        session.setAttribute("maTTInSession", maTT);

        String url = "https://accounts.google.com/o/oauth2/v2/auth"+ "?client_id="+ URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8)
                + "&redirect_uri="+ URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)+ "&response_type=code" + "&scope="+ URLEncoder.encode("openid email profile", StandardCharsets.UTF_8)
                + "&state="+ URLEncoder.encode(maTT,StandardCharsets.UTF_8) + "&access_type=offline" + "&prompt=consent";
        resp.sendRedirect(url);
    }

}
