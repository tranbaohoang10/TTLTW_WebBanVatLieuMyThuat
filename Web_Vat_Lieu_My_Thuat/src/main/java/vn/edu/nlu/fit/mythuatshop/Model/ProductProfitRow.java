package vn.edu.nlu.fit.mythuatshop.Model;

import java.math.BigDecimal;

public class ProductProfitRow {
    private int productId;
    private String productName;

    private int importedQuantity;
    private BigDecimal totalImportCost;
    private BigDecimal averageImportPrice;

    private int soldQuantity;
    private BigDecimal revenue;
    private BigDecimal costOfGoodsSold;
    private BigDecimal profit;
    private BigDecimal profitMargin;

    public ProductProfitRow() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImportedQuantity() {
        return importedQuantity;
    }

    public void setImportedQuantity(int importedQuantity) {
        this.importedQuantity = importedQuantity;
    }

    public BigDecimal getTotalImportCost() {
        return totalImportCost;
    }

    public void setTotalImportCost(BigDecimal totalImportCost) {
        this.totalImportCost = totalImportCost;
    }

    public BigDecimal getAverageImportPrice() {
        return averageImportPrice;
    }

    public void setAverageImportPrice(BigDecimal averageImportPrice) {
        this.averageImportPrice = averageImportPrice;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getCostOfGoodsSold() {
        return costOfGoodsSold;
    }

    public void setCostOfGoodsSold(BigDecimal costOfGoodsSold) {
        this.costOfGoodsSold = costOfGoodsSold;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }
}