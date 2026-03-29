package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Service.GhnService;

import java.io.IOException;

@WebServlet(name = "GhnController", urlPatterns = {"/ghn/*"})
public class GhnController extends HttpServlet {
    private final GhnService ghnService = new GhnService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo(); // /provinces, /districts, /wards

        try {
            if (path == null) {
                resp.setStatus(404);
                resp.getWriter().write("{\"success\":false,\"message\":\"Missing path\"}");
                return;
            }

            if ("/provinces".equals(path)) {
                resp.getWriter().write(ghnService.getProvincesRawJson());
                return;
            }

            if ("/districts".equals(path)) {
                String p = req.getParameter("provinceId");
                if (p == null || p.isBlank()) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"success\":false,\"message\":\"provinceId is required\"}");
                    return;
                }
                int provinceId = Integer.parseInt(p);
                resp.getWriter().write(ghnService.getDistrictsRawJson(provinceId));
                return;
            }
            if ("/wards".equals(path)) {
                String d = req.getParameter("districtId");
                if (d == null || d.isBlank()) {
                    resp.setStatus(400);
                    resp.getWriter().write("{\"success\":false,\"message\":\"districtId is required\"}");
                    return;
                }
                int districtId = Integer.parseInt(d);
                resp.getWriter().write(ghnService.getWardsRawJson(districtId));
                return;
            }


            resp.setStatus(404);
            resp.getWriter().write("{\"success\":false,\"message\":\"Not found\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"success\":false,\"message\":\"" +
                    e.getMessage().replace("\"", "'") + "\"}");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo();

        if (!"/fee".equals(path)) {
            resp.setStatus(404);
            resp.getWriter().write("{\"success\":false,\"message\":\"Not found\"}");
            return;
        }

        try {
            int districtId = Integer.parseInt(req.getParameter("districtId"));
            String wardCode = req.getParameter("wardCode");

            HttpSession session = req.getSession();
            Cart cartTemp = (Cart) session.getAttribute("cartTemp");
            if (cartTemp == null || cartTemp.cartSize() == 0) {
                resp.getWriter().write("{\"success\":false,\"message\":\"Cart empty\"}");
                return;
            }

            int fee = ghnService.calculateFee(districtId, wardCode);

            cartTemp.setFee(fee);
            session.setAttribute("cartTemp", cartTemp);

            long totalToPay = Math.round(cartTemp.getTotalPriceToPay());

            resp.getWriter().write("{"
                    + "\"success\":true,"
                    + "\"fee\":" + fee + ","
                    + "\"totalToPay\":" + totalToPay
                    + "}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"success\":false,\"message\":\"" +
                    e.getMessage().replace("\"", "'") + "\"}");
        }
    }
}