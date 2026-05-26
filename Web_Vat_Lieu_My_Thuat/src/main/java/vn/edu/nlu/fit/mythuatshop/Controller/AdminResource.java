package vn.edu.nlu.fit.mythuatshop.Controller;
import java.util.HashMap;
import java.util.Map;

public class AdminResource {
    private static final Map<String, String> resources = new HashMap<>();

    static {
        resources.put("/admin/overview", "DASHBOARD_VIEW");
        resources.put("/admin/statistics", "STATISTIC_VIEW");

        resources.put("/admin/categories", "CATEGORY_VIEW");
        resources.put("/admin/products", "PRODUCT_VIEW");
        resources.put("/admin/inventory", "INVENTORY_VIEW");
        resources.put("/admin/suppliers", "SUPPLIER_VIEW");
        resources.put("/admin/users", "USER_VIEW");

        resources.put("/admin/orders", "ORDER_VIEW");
        resources.put("/admin/order-detail", "ORDER_DETAIL_VIEW");
        resources.put("/admin/orders/edit", "ORDER_EDIT_VIEW");
        resources.put("/admin/orders/status", "ORDER_STATUS_VIEW");

        resources.put("/admin/vouchers", "VOUCHER_VIEW");
        resources.put("/admin/sliders", "SLIDER_VIEW");

        resources.put("/admin/contacts", "CONTACT_VIEW");
        resources.put("/admin/contacts/delete", "CONTACT_DELETE_VIEW");
        resources.put("/admin/contacts/reply", "CONTACT_REPLY_VIEW");

        resources.put("/admin/logs", "LOG_VIEW");
        resources.put("/admin/log-detail", "LOG_DETAIL_VIEW");
    }

    public static String getPermissionCode(String path) {
        return resources.get(path);
    }
}