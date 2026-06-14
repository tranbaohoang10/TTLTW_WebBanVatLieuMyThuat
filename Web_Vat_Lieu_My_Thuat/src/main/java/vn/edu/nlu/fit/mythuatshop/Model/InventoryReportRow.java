package vn.edu.nlu.fit.mythuatshop.Model;

import java.sql.Timestamp;

public class InventoryReportRow {
    private int productId;
    private String productCode;
    private String productName;
    private String categoryName;
    private String brand;
    private double price;
    private int discountDefault;
    private int quantityStock;
    private int soldQuantity;
    private int totalImported;
    private int totalSale;
    private int totalCancel;
    private int totalAdjust;
    private int isActive;
    private Timestamp lastTransactionAt;

    public InventoryReportRow() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscountDefault() {
        return discountDefault;
    }

    public void setDiscountDefault(int discountDefault) {
        this.discountDefault = discountDefault;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getTotalImported() {
        return totalImported;
    }

    public void setTotalImported(int totalImported) {
        this.totalImported = totalImported;
    }

    public int getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(int totalSale) {
        this.totalSale = totalSale;
    }

    public int getTotalCancel() {
        return totalCancel;
    }

    public void setTotalCancel(int totalCancel) {
        this.totalCancel = totalCancel;
    }

    public int getTotalAdjust() {
        return totalAdjust;
    }

    public void setTotalAdjust(int totalAdjust) {
        this.totalAdjust = totalAdjust;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Timestamp getLastTransactionAt() {
        return lastTransactionAt;
    }

    public void setLastTransactionAt(Timestamp lastTransactionAt) {
        this.lastTransactionAt = lastTransactionAt;
    }
    public double getPriceAfterDiscount() {
        return price * (100.0 - discountDefault) / 100.0;
    }

    public double getStockValue() {
        return quantityStock * getPriceAfterDiscount();
    }

    public String getStockStatus() {
        if (quantityStock <= 0) {
            return "Đã hết hàng";
        }
        if (quantityStock <= 10) {
            return "Sắp hết hàng";
        }
        return "Còn hàng";
    }
    public String getActiveStatus() {
        return isActive == 1 ? "Đang bán" : "Ngừng bán";
    }
}
