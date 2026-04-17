package vn.edu.nlu.fit.mythuatshop.Service;

public class VoucherApplyResult {
    private boolean success;
    private String message;
    private String voucherType;
    private double productDiscount;
    private double shippingDiscount;
    private double totalDiscount;

    public static VoucherApplyResult ok(String voucherType, double productDiscount, double shippingDiscount) {
        VoucherApplyResult r = new VoucherApplyResult();
        r.success = true;
        r.message = "Áp dụng voucher thành công";
        r.voucherType = voucherType;
        r.productDiscount = productDiscount;
        r.shippingDiscount = shippingDiscount;
        r.totalDiscount = productDiscount + shippingDiscount;
        return r;
    }

    public static VoucherApplyResult fail(String msg) {
        VoucherApplyResult r = new VoucherApplyResult();
        r.success = false;
        r.message = msg;
        r.voucherType = "";
        r.productDiscount = 0;
        r.shippingDiscount = 0;
        r.totalDiscount = 0;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public double getShippingDiscount() {
        return shippingDiscount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }public double getDiscount() {
        return totalDiscount;
    }

}
