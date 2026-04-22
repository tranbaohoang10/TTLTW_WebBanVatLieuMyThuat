package vn.edu.nlu.fit.mythuatshop.Controller;


import vn.edu.nlu.fit.mythuatshop.Model.GhnCreateResult;
import vn.edu.nlu.fit.mythuatshop.Model.Order;
import vn.edu.nlu.fit.mythuatshop.Service.GhnService;
import vn.edu.nlu.fit.mythuatshop.Service.OrderService;

public class TestCreateGhnOrder {
    public static void main(String[] args) {
        try {
            OrderService orderService = new OrderService();
            GhnService ghnService = new GhnService();

            int orderId = 70;
            Order order = orderService.getOrderDetailForAdmin(orderId);

            if (order == null) {
                System.out.println("Không tìm thấy đơn hàng");
                return;
            }

            if (order.getViewItems() == null || order.getViewItems().isEmpty()) {
                System.out.println("Đơn hàng không có sản phẩm");
                return;
            }

            System.out.println("Order id: " + order.getId());
            System.out.println("Full name: " + order.getFullName());
            System.out.println("Phone: " + order.getPhoneNumber());
            System.out.println("Address: " + order.getAddress());
            System.out.println("District id: " + order.getDeliveryDistrictId());
            System.out.println("Ward code: " + order.getDeliveryWardCode());
            System.out.println("Payment status: " + order.getPaymentStatus());

            GhnCreateResult result = ghnService.createShippingOrder(order, order.getViewItems());

            System.out.println("=== TAO VAN DON THANH CONG ===");
            System.out.println("GHN order code: " + result.getOrderCode());
            System.out.println("Client order code: " + result.getClientOrderCode());
            System.out.println("Expected delivery time: " + result.getExpectedDeliveryTime());
            System.out.println("Expected delivery date: " + result.getExpectedDeliveryDateText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
