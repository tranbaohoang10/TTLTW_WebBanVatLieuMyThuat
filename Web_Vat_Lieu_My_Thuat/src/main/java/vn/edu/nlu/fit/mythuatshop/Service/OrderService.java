package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.OrderDao;
import vn.edu.nlu.fit.mythuatshop.Dao.OrderStatusDao;
import vn.edu.nlu.fit.mythuatshop.Dao.PaymentDao;
import vn.edu.nlu.fit.mythuatshop.Dao.VoucherDao;
import vn.edu.nlu.fit.mythuatshop.Model.*;
import vn.edu.nlu.fit.mythuatshop.Util.EmailUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    private final PaymentDao paymentDao;
    private final OrderStatusDao orderStatusDao;
    private final GhnService ghnService;
    public OrderService() {
        this.orderDao = new OrderDao();
        this.paymentDao = new PaymentDao();
        this.orderStatusDao = new OrderStatusDao();
        this.ghnService = new GhnService();

    }

    public Order createOrder(Users user, Cart cart, String fullName, String email, String phone,
                             String address, String note, String paymentName, Integer voucherId,String provinceId, String districtId, String wardCode) {

        if (user == null || cart == null || cart.cartSize() == 0)
            return null;

        boolean isVnpay = "VNPAY".equalsIgnoreCase(paymentName);

        Order order = new Order();
        order.setUserId(user.getId());
        order.setFullName(fullName);
        order.setEmail(email);
        order.setPhoneNumber(phone);
        order.setAddress(address);
        order.setNote(note);
        if (provinceId != null && !provinceId.isBlank()) {
            order.setDeliveryProvinceId(Integer.parseInt(provinceId));
        }

        if (districtId != null && !districtId.isBlank()) {
            order.setDeliveryDistrictId(Integer.parseInt(districtId));
        }

        order.setDeliveryWardCode(wardCode);
        order.setExpectedDeliveryTime(cart.getExpectedDeliveryTime());
        order.setExpectedDeliveryDateText(cart.getExpectedDeliveryDateText());

        Payment payment = paymentDao.findByName(paymentName);
        if (payment == null) {
            payment = paymentDao.findByName("COD");
        }
        order.setPaymentId(payment.getId());

        OrderStatus status = orderStatusDao.findByName("Đang xử lý");
        if (status != null) {
            order.setOrderStatusId(status.getId());
        } else {
            order.setOrderStatusId(1);
        }

        order.setPaymentStatus("Chưa thanh toán");

        order.setDiscount(cart.getDiscount());
        order.setVoucherId(voucherId);
        order.setShippingFee(cart.getFee());
        order.setTotalPrice(cart.getTotalPriceToPay());

        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cart.getCarts().values()) {
            OrderDetail d = new OrderDetail();
            d.setProductId(item.getProductId());
            d.setQuantity(item.getQuantity());
            d.setPrice(item.getPriceAfterDiscount());
            details.add(d);
        }
        order.setItems(details);

        try {
            int newId = orderDao.insert(order, !isVnpay);
            if (newId <= 0)
                return null;

            order.setId(newId);
            if (!isVnpay && voucherId != null) {
                try {
                    VoucherDao voucherDao = new VoucherDao();
                    voucherDao.increaseUsed(voucherId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (!isVnpay) {
                try {
                    String subject = "Xác nhận đơn hàng #DH" + newId;
                    String html = buildOrderEmailHtml(order, cart, paymentName);
                    EmailUtil.sendHtml(order.getEmail(), subject, html);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return order;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order confirmVnpayPaid(int orderId, long amountVnd) {
        try {
            Order order = orderDao.confirmVnpayPaid(orderId, amountVnd);
            if (order != null) {
                Integer vid = order.getVoucherId();
                if (vid != null) {
                    try {
                        new VoucherDao().increaseUsed(vid);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    String subject = "Xác nhận đơn hàng #DH" + orderId;
                    String html = buildVnpayOrderEmailHtml(order);
                    EmailUtil.sendHtml(order.getEmail(), subject, html);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void markVnpayFailed(int orderId) {
        try {
            orderDao.markVnpayFailed(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrderHistory(int userId, String status) {
        Integer statusId = mapStatusToId(status);

        List<Order> orders = orderDao.findOrdersByUser(userId, statusId);
        for (Order o : orders) {
            List<OrderItem> items = orderDao.findOrderItemsView(o.getId());
            o.setViewItems(items);
        }
        return orders;
    }

    private Integer mapStatusToId(String status) {
        if (status == null || status.isBlank() || "all".equalsIgnoreCase(status)) {
            return null;
        }
        return switch (status.toLowerCase()) {
            case "pending" -> 1;
            case "shipping" -> 2;
            case "completed" -> 3;
            case "cancelled" -> 4;
            default -> null;
        };
    }

    public Order getOrderDetail(int userId, int orderId) {
        Order order = orderDao.findOrderHeaderByIdAndUser(orderId, userId);
        if (order == null)
            return null;

        order.setViewItems(orderDao.findOrderItemsView(orderId));
        return order;
    }

    public boolean cancelOrder(int userId, int orderId, String cancelReason) {
        return orderDao.cancelOrder(userId, orderId, cancelReason);
    }


    private String buildOrderEmailHtml(Order order, Cart cart, String paymentName) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>Thiên Long - Xác nhận đơn hàng</h2>");
        sb.append("<p>Chào ").append(escape(order.getFullName())).append(",</p>");

        sb.append("<p>");
        sb.append("Mã đơn hàng: <b>#").append(order.getId()).append("</b><br/>");
        sb.append("SĐT: ").append(escape(order.getPhoneNumber())).append("<br/>");
        sb.append("Địa chỉ: ").append(escape(order.getAddress())).append("<br/>");
        sb.append("Thanh toán: ").append(escape(paymentName)).append("<br/>");
        sb.append("</p>");

        if (order.getNote() != null && !order.getNote().trim().isEmpty()) {
            sb.append("<p>Ghi chú: ").append(escape(order.getNote())).append("</p>");
        }

        sb.append("<p><b>Danh sách sản phẩm:</b></p>");
        sb.append("<ul>");
        for (CartItem item : cart.getCarts().values()) {
            sb.append("<li>")
                    .append("SP #").append(item.getProductId())
                    .append(" - SL: ").append(item.getQuantity())
                    .append(" - Giá: ").append(formatVnd(item.getPriceAfterDiscount()))
                    .append("</li>");
        }
        sb.append("</ul>");

        sb.append("<p>");
        sb.append("Giảm giá: <b>").append(formatVnd(order.getDiscount())).append("</b><br/>");
        sb.append("Tổng thanh toán: <b>").append(formatVnd(order.getTotalPrice())).append("</b>");
        sb.append("</p>");

        sb.append("<p>Cảm ơn bạn đã mua hàng tại Thiên Long!</p>");

        return sb.toString();
    }

    private String buildVnpayOrderEmailHtml(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>Thiên Long - Xác nhận đơn hàng</h2>");
        sb.append("<p>Chào ").append(escape(order.getFullName())).append(",</p>");

        sb.append("<p>");
        sb.append("Mã đơn hàng: <b>#DH").append(order.getId()).append("</b><br/>");
        sb.append("SĐT: ").append(escape(order.getPhoneNumber())).append("<br/>");
        sb.append("Địa chỉ: ").append(escape(order.getAddress())).append("<br/>");
        sb.append("Thanh toán: <b>VNPAY</b> - Đã thanh toán thành công<br/>");
        sb.append("</p>");

        if (order.getNote() != null && !order.getNote().trim().isEmpty()) {
            sb.append("<p>Ghi chú: ").append(escape(order.getNote())).append("</p>");
        }

        sb.append("<p><b>Danh sách sản phẩm:</b></p>");
        sb.append("<ul>");
        if (order.getItems() != null) {
            for (OrderDetail item : order.getItems()) {
                sb.append("<li>")
                        .append("SP #").append(item.getProductId())
                        .append(" - SL: ").append(item.getQuantity())
                        .append(" - Giá: ").append(formatVnd(item.getPrice()))
                        .append("</li>");
            }
        }
        sb.append("</ul>");

        sb.append("<p>");
        sb.append("Giảm giá: <b>").append(formatVnd(order.getDiscount())).append("</b><br/>");
        sb.append("Tổng thanh toán: <b>").append(formatVnd(order.getTotalPrice())).append("</b>");
        sb.append("</p>");

        sb.append("<p>Cảm ơn bạn đã mua hàng tại Thiên Long!</p>");

        return sb.toString();
    }

    private String formatVnd(double value) {
        long v = Math.round(value);
        return String.format("%,d", v).replace(',', '.') + " đ";
    }

    private String escape(String s) {
        if (s == null)
            return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public List<Order> getAllForAdmin() {
        return orderDao.findAllForAdmin();
    }

    public boolean adminUpdateOrderStatus(int orderId, String newStatusName) {
        Order o = orderDao.findOrderForAdmin(orderId);
        if (o == null) return false;

        String current = o.getStatusName();
        String payName = o.getPaymentName();
        String payStatus = o.getPaymentStatus();

        if ("Hoàn thành".equalsIgnoreCase(current) || "Đã hủy".equalsIgnoreCase(current)) {
            return false;
        }

        boolean isVnpay = "VNPAY".equalsIgnoreCase(payName);

        if (isVnpay) {
            if (!"Đã thanh toán".equalsIgnoreCase(payStatus)) {
                if ("Đang vận chuyển".equalsIgnoreCase(newStatusName) || "Hoàn thành".equalsIgnoreCase(newStatusName)) {
                    return false;
                }
            }
        }

        boolean allowed =
                ("Đang xử lý".equalsIgnoreCase(current) &&
                        ("Đang vận chuyển".equalsIgnoreCase(newStatusName) || "Đã hủy".equalsIgnoreCase(newStatusName)))
                        ||
                        ("Đang vận chuyển".equalsIgnoreCase(current) &&
                                "Hoàn thành".equalsIgnoreCase(newStatusName));

        if (!allowed) return false;
        if ("Đang vận chuyển".equalsIgnoreCase(newStatusName)) {
            Order fullOrder = getOrderDetailForAdmin(orderId);
            if (fullOrder == null) {
                return false;
            }

            try {
                if (fullOrder.getGhnOrderCode() == null || fullOrder.getGhnOrderCode().isBlank()) {
                    GhnCreateResult result = ghnService.createShippingOrder(fullOrder, fullOrder.getViewItems());

                    if (result == null || result.getOrderCode() == null || result.getOrderCode().isBlank()) {
                        return false;
                    }


                    boolean saved = orderDao.updateGhnInfo(orderId, result.getOrderCode(), "created");
                    if (!saved) {
                        return false;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        boolean ok = orderDao.updateOrderStatusByName(orderId, newStatusName);
        if (!ok) return false;

        if ("COD".equalsIgnoreCase(payName) && "Hoàn thành".equalsIgnoreCase(newStatusName)) {
            orderDao.updatePaymentStatus(orderId, "Đã thanh toán");
        }

        return true;
    }

    public boolean adminUpdateOrderInfo(int orderId, String fullName, String phone, String address) {
        return orderDao.updateInfoIfProcessing(orderId, fullName, phone, address);
    }
    public Order getOrderDetailForAdmin(int orderId) {
        Order order = orderDao.findOrderById(orderId);
        if (order == null) {
            return null;
        }

        List<OrderItem> items = orderDao.findOrderItemsView(orderId);
        order.setViewItems(items);

        return order;
    }


}
