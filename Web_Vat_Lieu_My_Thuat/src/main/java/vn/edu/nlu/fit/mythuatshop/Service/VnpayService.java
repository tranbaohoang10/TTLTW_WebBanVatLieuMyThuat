package vn.edu.nlu.fit.mythuatshop.Service;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Util.ConfigVNPay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class VnpayService {
    public String buildPaymentUrl(HttpServletRequest req, Order order) {

        long amountVnd = BigDecimal.valueOf(order.getTotalPrice())
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
        long vnpAmount = amountVnd * 100;
        String txnRef = order.getId() + "_" + System.currentTimeMillis();
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", ConfigVNPay.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", txnRef);

        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang DH" + order.getId());
        vnp_Params.put("vnp_OrderType", "other");

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ConfigVNPay.getIpAddress(req));
        TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(vnTimeZone);

        Date createDate = new Date();
        vnp_Params.put("vnp_CreateDate", formatter.format(createDate));

        Calendar cal = Calendar.getInstance(vnTimeZone);
        cal.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cal.getTime()));
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue == null || fieldValue.isEmpty()) continue;

            String encodedFieldName = java.net.URLEncoder.encode(fieldName, java.nio.charset.StandardCharsets.UTF_8);
            String encodedFieldValue = java.net.URLEncoder.encode(fieldValue, java.nio.charset.StandardCharsets.UTF_8);

            hashData.append(fieldName).append("=").append(encodedFieldValue);
            query.append(encodedFieldName).append("=").append(encodedFieldValue);

            if (i < fieldNames.size() - 1) {
                hashData.append("&");
                query.append("&");
            }
        }
        String secureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.secretKey, hashData.toString());
        return ConfigVNPay.vnp_PayUrl + "?" + query + "&vnp_SecureHash=" + secureHash;
    }


    public boolean verifyReturn(HttpServletRequest req) {
        String secureHash = req.getParameter("vnp_SecureHash");
        if (secureHash == null || secureHash.isEmpty()) return false;

        Map<String, String> fields = new HashMap<>();
        Enumeration<String> en = req.getParameterNames();

        while (en.hasMoreElements()) {
            String name = en.nextElement();
            if (name.startsWith("vnp_")
                    && !name.equals("vnp_SecureHash")
                    && !name.equals("vnp_SecureHashType")) {
                String value = req.getParameter(name); // đã decode
                if (value != null && !value.isEmpty()) {
                    fields.put(name, value);
                }
            }
        }

        List<String> keys = new ArrayList<>(fields.keySet());
        Collections.sort(keys);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String k = keys.get(i);
            String v = fields.get(k);

            String encodedV = java.net.URLEncoder.encode(v, java.nio.charset.StandardCharsets.UTF_8);

            hashData.append(k).append("=").append(encodedV);
            if (i < keys.size() - 1) hashData.append("&");
        }

        String signValue = ConfigVNPay.hmacSHA512(ConfigVNPay.secretKey, hashData.toString());
        return signValue != null && signValue.equalsIgnoreCase(secureHash);
    }

    private String nowyyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
