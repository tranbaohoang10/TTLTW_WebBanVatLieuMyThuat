package vn.edu.nlu.fit.mythuatshop.Model;

import java.sql.Timestamp;

public class InventoryTransaction {
    private int id;
    private int productId;
    private String productName;
    private String type;
    private int quantity;
    private int beforeStock;
    private int afterStock;
    private String note;
    private Integer orderId;
    private Integer createdBy;
    private Timestamp createAt;

    public InventoryTransaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getBeforeStock() {
        return beforeStock;
    }

    public void setBeforeStock(int beforeStock) {
        this.beforeStock = beforeStock;
    }


    public int getAfterStock() {
        return afterStock;
    }

    public void setAfterStock(int afterStock) {
        this.afterStock = afterStock;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}