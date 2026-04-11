package vn.edu.nlu.fit.mythuatshop.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Voucher implements Serializable {
    private int id;
    private String code;
    private String name;
    private String description;
    private double voucherCash;
    private double minOrderValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int quantity;
    private int quantityUsed;
    private int isActive;
    private String voucherType;
    private Double voucherPercent;
    private Double maxDiscount;

    public Voucher() {
    }

    public Voucher(int id, String code, String name, String description, double voucherCash, double minOrderValue, LocalDateTime startDate, LocalDateTime endDate, int quantity, int quantityUsed, int isActive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.voucherCash = voucherCash;
        this.minOrderValue = minOrderValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.quantityUsed = quantityUsed;
        this.isActive = isActive;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Double getVoucherPercent() {
        return voucherPercent;
    }

    public void setVoucherPercent(Double voucherPercent) {
        this.voucherPercent = voucherPercent;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getVoucherCash() {
        return voucherCash;
    }

    public void setVoucherCash(double voucherCash) {
        this.voucherCash = voucherCash;
    }

    public double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}

