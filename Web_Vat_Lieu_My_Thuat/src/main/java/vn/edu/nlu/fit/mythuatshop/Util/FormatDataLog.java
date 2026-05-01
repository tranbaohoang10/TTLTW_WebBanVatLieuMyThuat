package vn.edu.nlu.fit.mythuatshop.Util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class FormatDataLog {
    public static String format(String data){
        if(data==null || data.isBlank()){
            return "Không có dữ liệu";
        }
        try{
            JsonElement je = JsonParser.parseString(data);
            if(!je.isJsonObject()){
                return data;
            }
            JsonObject jo = je.getAsJsonObject();
            Map<String,String> map = getFieldName();
            StringBuilder result = new StringBuilder();
            for(String key : map.keySet()){
                if(!jo.has(key) || jo.get(key).isJsonNull()){
                    continue;
                }
                String value = jo.get(key).getAsString();
                result.append(map.get(key)).append(": ").append(formatValue(key, value)).append("\n");
            }
            if(result.length() == 0){
                return "Không có dữ liệu";
            }
            return result.toString();
        }
        catch (Exception e){
            return data;
        }
    }

    private static Map<String, String> getFieldName() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("id", "Mã dữ liệu");
        map.put("name", "Tên");
        map.put("title", "Tiêu đề");
        map.put("description", "Mô tả");
        map.put("createAt", "Ngày tạo");

        map.put("fullName", "Họ và tên");
        map.put("email", "Email");
        map.put("phoneNumber", "Số điện thoại");
        map.put("dob", "Ngày sinh");
        map.put("address", "Địa chỉ");
        map.put("role", "Vai trò");
        map.put("isActive", "Trạng thái");

        map.put("price", "Giá");
        map.put("discountDefault", "Giá gốc");
        map.put("categoryId", "Mã danh mục");
        map.put("thumbnail", "Ảnh");
        map.put("quantityStock", "Số lượng tồn");
        map.put("soldQuantity", "Số lượng đã bán");
        map.put("status", "Trạng thái");
        map.put("brand", "Thương hiệu");

        map.put("code", "Mã khuyến mãi");
        map.put("voucherType", "Loại khuyến mãi");
        map.put("voucherCash", "Số tiền giảm");
        map.put("minOrderValue", "Đơn tối thiểu");
        map.put("startDate", "Ngày bắt đầu");
        map.put("endDate", "Ngày kết thúc");
        map.put("quantity", "Số lượng");
        map.put("quantityUsed", "Số lượng đã dùng");
        map.put("voucherPercent", "Phần trăm giảm");
        map.put("maxDiscount", "Giảm tối đa");

        map.put("linkTo", "Đường dẫn");
        map.put("indexOder", "Thứ tự hiển thị");

        return map;

    }
    private static String formatValue(String key, String value){
        if(value==null || value.isBlank()){
            return "Không có dữ liệu";
        }
        if("role".equals(key)){
            if("ADMIN".equalsIgnoreCase(value)){
                return "Quản trị viên";
            }
            if("USER".equalsIgnoreCase(value)){
                return "Khách hàng";
            }
        }
        if("isActive".equals(key)){
            if("0".equals(value)){
                return "Không hoạt động";
            }
            if("1".equals(value)){
                return "Đang hoạt động";
            }
            if("3".equals(value)){
                return "Đã khóa";
            }
        }
        if("paymentMethod".equals(key)){
            if("COD".equalsIgnoreCase(value)){
                return "Thanh toán khi nhận hàng";
            }
            if("VNPAY".equalsIgnoreCase(value)){
                return "Thanh toán bằng VNPAY";
            }
        }
        return value;
    }
}
