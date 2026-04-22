package vn.edu.nlu.fit.mythuatshop.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import vn.edu.nlu.fit.mythuatshop.Model.GhnCreateResult;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Model.OrderItem;
import vn.edu.nlu.fit.mythuatshop.Util.GhnConfig;
import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingInfo;
import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingLog;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GhnService {
    private final GhnConfig cfg = GhnConfig.load();
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper om = new ObjectMapper();


    public String getProvincesRawJson() throws Exception {
        String url = cfg.baseUrl + "/shiip/public-api/master-data/province";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN error: HTTP " + resp.statusCode() + " - " + resp.body());
        }
        return resp.body();
    }

    public String getDistrictsRawJson(int provinceId) throws Exception {
        String url = cfg.baseUrl + "/shiip/public-api/master-data/district";
        String body = "{\"province_id\":" + provinceId + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN error: HTTP " + resp.statusCode() + " - " + resp.body());
        }
        return resp.body();
    }

    public String getWardsRawJson(int districtId) throws Exception {
        String url = cfg.baseUrl + "/shiip/public-api/master-data/ward?district_id";
        String body = "{\"district_id\":" + districtId + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                // GHN ward cũng dùng GET + body
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN error: HTTP " + resp.statusCode() + " - " + resp.body());
        }
        return resp.body();
    }

    public int getFirstServiceId(int toDistrictId) throws Exception {
        String url = cfg.baseUrl + "/shiip/public-api/v2/shipping-order/available-services";

        String body = "{"
                + "\"shop_id\":" + cfg.shopId + ","
                + "\"from_district\":" + cfg.fromDistrictId + ","
                + "\"to_district\":" + toDistrictId
                + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .header("ShopId", String.valueOf(cfg.shopId))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN service error: HTTP " + resp.statusCode() + " - " + resp.body());
        }

        JsonNode root = om.readTree(resp.body());
        JsonNode data = root.path("data");
        if (!data.isArray() || data.size() == 0) return -1;
        return data.get(0).path("service_id").asInt(-1);
    }



    public int calculateFee(int toDistrictId, String toWardCode) throws Exception {
        int serviceId = getFirstServiceId(toDistrictId);
        if (serviceId <= 0) return 0;

        String url = cfg.baseUrl + "/shiip/public-api/v2/shipping-order/fee";
        String body = "{"
                + "\"from_district_id\":" + cfg.fromDistrictId + ","
                + "\"from_ward_code\":\"" + cfg.fromWardCode + "\","
                + "\"service_id\":" + serviceId + ","
                + "\"to_district_id\":" + toDistrictId + ","
                + "\"to_ward_code\":\"" + toWardCode + "\","
                + "\"height\":" + cfg.height + ","
                + "\"length\":" + cfg.length + ","
                + "\"weight\":" + cfg.weight + ","
                + "\"width\":" + cfg.width + ","
                + "\"insurance_value\":" + cfg.insuranceValue
                + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .header("ShopId", String.valueOf(cfg.shopId))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN fee error: HTTP " + resp.statusCode() + " - " + resp.body());
        }

        JsonNode root = om.readTree(resp.body());
        JsonNode data = root.path("data");

        int fee = data.path("total").asInt(-1);
        if (fee < 0) fee = data.path("total_fee").asInt(0);
        return Math.max(fee, 0);
    }
    public Long getExpectedDeliveryTime(int toDistrictId, String toWardCode) throws Exception {
        int serviceId = getFirstServiceId(toDistrictId);
        if (serviceId <= 0) {
            return null;
        }

        String url = cfg.baseUrl + "/shiip/public-api/v2/shipping-order/leadtime";
        String body = "{"
                + "\"from_district_id\":" + cfg.fromDistrictId + ","
                + "\"from_ward_code\":\"" + cfg.fromWardCode + "\","
                + "\"to_district_id\":" + toDistrictId + ","
                + "\"to_ward_code\":\"" + toWardCode + "\","
                + "\"service_id\":" + serviceId
                + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .header("ShopId", String.valueOf(cfg.shopId))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN leadtime error: HTTP " + resp.statusCode() + " - " + resp.body());
        }

        JsonNode root = om.readTree(resp.body());
        JsonNode data = root.path("data");

        long expectedDeliveryTime = data.path("leadtime").asLong(0);
        if (expectedDeliveryTime <= 0) {
            return null;
        }

        return expectedDeliveryTime;
    }
    public String formatExpectedDeliveryDate(Long expectedDeliveryTime) {
        if (expectedDeliveryTime == null || expectedDeliveryTime <= 0) {
            return "";
        }

        return Instant.ofEpochSecond(expectedDeliveryTime)
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public GhnCreateResult createShippingOrder(Order order, List<OrderItem> items) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("Order không được null");
        }
        if (order.getDeliveryDistrictId() == null || order.getDeliveryWardCode() == null || order.getDeliveryWardCode().isBlank()) {
            throw new IllegalArgumentException("Thiếu thông tin khu vực giao hàng");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng không có sản phẩm");
        }

        int serviceId = getFirstServiceId(order.getDeliveryDistrictId());
        if (serviceId <= 0) {
            throw new RuntimeException("Không lấy được service_id từ GHN");
        }

        String url = cfg.baseUrl + "/shiip/public-api/v2/shipping-order/create";

        ObjectNode body = om.createObjectNode();
        body.put("payment_type_id", 1);
        body.put("note", safe(order.getNote()));
        body.put("required_note", "KHONGCHOXEMHANG");
        body.put("to_name", safe(order.getFullName()));
        body.put("to_phone", safe(order.getPhoneNumber()));
        body.put("to_address", safe(order.getAddress()));
        body.put("to_ward_code", safe(order.getDeliveryWardCode()));
        body.put("to_district_id", order.getDeliveryDistrictId());
        body.put("cod_amount", getCodAmount(order));
        body.put("content", buildContent(items));
        body.put("weight", cfg.weight);
        body.put("length", cfg.length);
        body.put("width", cfg.width);
        body.put("height", cfg.height);
        body.put("insurance_value", (int) Math.round(order.getTotalPrice()));
        body.put("service_id", serviceId);
        body.put("client_order_code", "DH" + order.getId());

        ArrayNode itemArray = body.putArray("items");
        for (OrderItem item : items) {
            ObjectNode itemNode = itemArray.addObject();
            itemNode.put("name", safe(item.getName()));
            itemNode.put("quantity", item.getQuantity());
            itemNode.put("price", (int) Math.round(item.getPrice()));
            itemNode.put("length", cfg.length);
            itemNode.put("width", cfg.width);
            itemNode.put("height", cfg.height);
            itemNode.put("weight", cfg.weight);
        }

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .header("ShopId", String.valueOf(cfg.shopId))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN create error: HTTP " + resp.statusCode() + " - " + resp.body());
        }

        JsonNode root = om.readTree(resp.body());
        int code = root.path("code").asInt(-1);
        if (code != 200) {
            throw new RuntimeException("GHN create error: " + resp.body());
        }

        JsonNode data = root.path("data");

        GhnCreateResult result = new GhnCreateResult();
        result.setOrderCode(data.path("order_code").asText(""));
        result.setClientOrderCode(data.path("client_order_code").asText(""));
        long expectedTime = data.path("expected_delivery_time").asLong(0);
        if (expectedTime > 0) {
            result.setExpectedDeliveryTime(expectedTime);
            result.setExpectedDeliveryDateText(formatExpectedDeliveryDate(expectedTime));
        }

        return result;
    }

    private int getCodAmount(Order order) {
        if (order == null) return 0;
        if ("Đã thanh toán".equalsIgnoreCase(order.getPaymentStatus())) {
            return 0;
        }
        return (int) Math.round(order.getTotalPrice());
    }

    private String buildContent(List<OrderItem> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(items.get(i).getName());
        }
        String content = sb.toString().trim();
        if (content.isEmpty()) {
            return "Đơn hàng mỹ thuật";
        }
        return content.length() > 200 ? content.substring(0, 200) : content;
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
    public GhnTrackingInfo getTrackingDetail(String orderCode) throws Exception {
        if (orderCode == null || orderCode.isBlank()) {
            throw new IllegalArgumentException("orderCode không được để trống");
        }

        String url = cfg.baseUrl + "/shiip/public-api/v2/shipping-order/detail";
        String body = "{"
                + "\"order_code\":\"" + orderCode + "\""
                + "}";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Token", cfg.token)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new RuntimeException("GHN detail error: HTTP " + resp.statusCode() + " - " + resp.body());
        }

        JsonNode root = om.readTree(resp.body());
        int code = root.path("code").asInt(-1);
        if (code != 200) {
            throw new RuntimeException("GHN detail error: " + resp.body());
        }

        JsonNode data = root.path("data");
        JsonNode orderNode = data.isArray() && data.size() > 0 ? data.get(0) : data;

        GhnTrackingInfo info = new GhnTrackingInfo();
        info.setOrderCode(orderNode.path("order_code").asText(""));
        info.setClientOrderCode(orderNode.path("client_order_code").asText(""));
        info.setStatus(orderNode.path("status").asText(""));

        info.setLeadtime(orderNode.path("expected_delivery_time").asText(""));

        info.setToName(orderNode.path("to_name").asText(""));
        info.setToPhone(orderNode.path("to_phone").asText(""));
        info.setToAddress(orderNode.path("to_address").asText(""));
        info.setFromName(orderNode.path("from_name").asText(""));
        info.setFromPhone(orderNode.path("from_phone").asText(""));
        info.setFromAddress(orderNode.path("from_address").asText(""));
        info.setNote(orderNode.path("note").asText(""));

        List<GhnTrackingLog> logs = new ArrayList<>();
        JsonNode logArray = orderNode.path("log");
        if (logArray.isArray()) {
            for (JsonNode logNode : logArray) {
                GhnTrackingLog log = new GhnTrackingLog();

                log.setStatus(logNode.path("status_name").asText(""));

                log.setUpdatedDate(logNode.path("updated_date").asText(""));

                logs.add(log);
            }
        }
        info.setLogs(logs);

        return info;
    }


}