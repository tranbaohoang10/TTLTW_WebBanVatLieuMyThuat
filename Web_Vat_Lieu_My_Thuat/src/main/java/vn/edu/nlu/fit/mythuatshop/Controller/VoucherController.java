package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.nlu.fit.mythuatshop.Model.Cart;
import vn.edu.nlu.fit.mythuatshop.Service.VoucherApplyResult;
import vn.edu.nlu.fit.mythuatshop.Service.VoucherService;

import java.io.IOException;

@WebServlet(name = "VoucherController", urlPatterns = {"/voucher/*"})
public class VoucherController extends HttpServlet {
    private final VoucherService voucherService = new VoucherService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        String path = req.getPathInfo(); // /apply
        if (path == null) path = "";

        if (!"/apply".equals(path)) {
            resp.setStatus(404);
            resp.getWriter().write("{\"success\":false,\"message\":\"Not found\"}");
            return;
        }

        try {
            HttpSession session = req.getSession();
            Cart cartTemp = (Cart) session.getAttribute("cartTemp");

            String code = req.getParameter("code");

            VoucherApplyResult result = voucherService.apply(code, cartTemp);

            if (result.isSuccess()) {
                session.setAttribute("cartTemp", cartTemp);
                Integer vid = null;
                try {
                    vid = cartTemp.getVoucherId();
                } catch (Exception ignore) {}

                if (vid != null) {
                    session.setAttribute("appliedVoucherId", vid);
                }
                double fee = cartTemp.getFee();
                double totalToPay = cartTemp.getTotalPriceToPay();

                resp.getWriter().write("{"
                        + "\"success\":true,"
                        + "\"message\":\"" + escape(result.getMessage()) + "\","
                        + "\"discount\":" + (long) result.getDiscount() + ","
                        + "\"fee\":" + (long) fee + ","
                        + "\"totalToPay\":" + (long) totalToPay
                        + "}");
            } else {
                resp.getWriter().write("{"
                        + "\"success\":false,"
                        + "\"message\":\"" + escape(result.getMessage()) + "\""
                        + "}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"success\":false,\"message\":\"" + escape(e.toString()) + "\"}");
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "'").replace("\n", " ").replace("\r", " ");


    }
}