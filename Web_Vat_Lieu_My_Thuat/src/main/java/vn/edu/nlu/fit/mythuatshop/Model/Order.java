package vn.edu.nlu.fit.mythuatshop.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Order implements Serializable {
    private int id;
    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    private double totalPrice;
    private int paymentId;
    private int orderStatusId;
    private Integer voucherId;
    private double discount;
    private Timestamp createAt;
    private String note;
    private List<OrderDetail> items;
    private String statusName;
    private String paymentName;
    private List<OrderItem> viewItems;
    private double shippingFee;
    private String productNames;
    private String paymentStatus;
    private Integer deliveryProvinceId;
    private Integer deliveryDistrictId;
    private String deliveryWardCode;
    private Long expectedDeliveryTime;
    private String expectedDeliveryDateText;
    private String cancelReason;
    private String ghnOrderCode;
    private String ghnStatus;
    public Order() {
    }

    public Integer getDeliveryProvinceId() {
        return deliveryProvinceId;
    }

    public void setDeliveryProvinceId(Integer deliveryProvinceId) {
        this.deliveryProvinceId = deliveryProvinceId;
    }

    public String getExpectedDeliveryDateText() {
        return expectedDeliveryDateText;
    }

    public void setExpectedDeliveryDateText(String expectedDeliveryDateText) {
        this.expectedDeliveryDateText = expectedDeliveryDateText;
    }
    public String getGhnOrderCode() {
        return ghnOrderCode;
    }

    public void setGhnOrderCode(String ghnOrderCode) {
        this.ghnOrderCode = ghnOrderCode;
    }

    public String getGhnStatus() {
        return ghnStatus;
    }

    public void setGhnStatus(String ghnStatus) {
        this.ghnStatus = ghnStatus;
    }
    public Long getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Long expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public String getDeliveryWardCode() {
        return deliveryWardCode;
    }

    public void setDeliveryWardCode(String deliveryWardCode) {
        this.deliveryWardCode = deliveryWardCode;
    }

    public Integer getDeliveryDistrictId() {
        return deliveryDistrictId;
    }

    public void setDeliveryDistrictId(Integer deliveryDistrictId) {
        this.deliveryDistrictId = deliveryDistrictId;
    }

    public String getProductNames() {
        return productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderDetail> getItems() {
        return items;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<OrderItem> getViewItems() {
        return viewItems;
    }

    public void setViewItems(List<OrderItem> viewItems) {
        this.viewItems = viewItems;
    }


    public void setItems(List<OrderDetail> items) {
        this.items = items;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
