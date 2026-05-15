package vn.edu.nlu.fit.mythuatshop.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

import java.io.IOException;

@WebServlet(name = "GhnWebhookController", value = "/webhook/ghn/order-status")
public class GhnWebhookController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            JsonNode root = objectMapper.readTree(req.getReader());

            String orderCode = root.path("OrderCode").asText("");
            String status = root.path("Status").asText("");
            String time = root.path("Time").asText("");
            String warehouse = root.path("Warehouse").asText("");
            String type = root.path("Type").asText("");

             boolean ok = orderService.updateGhnWebhookStatus(orderCode, status, time, warehouse);

            String json = "{"
                    + "\"success\":" + ok + ","
                    + "\"message\":\"Nhan webhook thanh cong\""
                    + "}";

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();

            String json = "{"
                    + "\"success\":false,"
                    + "\"message\":\"Loi xu ly webhook\""
                    + "}";

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(json);
        }
    }
}