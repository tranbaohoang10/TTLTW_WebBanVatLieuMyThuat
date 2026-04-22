package vn.edu.nlu.fit.mythuatshop.Model;

public class GhnCreateResult {
    private String orderCode;
    private String clientOrderCode;
    private Long expectedDeliveryTime;
    private String expectedDeliveryDateText;

    public GhnCreateResult() {
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getClientOrderCode() {
        return clientOrderCode;
    }

    public void setClientOrderCode(String clientOrderCode) {
        this.clientOrderCode = clientOrderCode;
    }

    public Long getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Long expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public String getExpectedDeliveryDateText() {
        return expectedDeliveryDateText;
    }

    public void setExpectedDeliveryDateText(String expectedDeliveryDateText) {
        this.expectedDeliveryDateText = expectedDeliveryDateText;
    }
}