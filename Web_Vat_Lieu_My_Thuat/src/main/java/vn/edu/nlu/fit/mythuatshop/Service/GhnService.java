package vn.edu.nlu.fit.mythuatshop.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.edu.nlu.fit.mythuatshop.Util.GhnConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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


}