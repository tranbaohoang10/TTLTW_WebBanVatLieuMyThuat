package vn.edu.nlu.fit.mythuatshop.Model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private int discountDefault;
    private int categoryId;
    private String thumbnail;
    private int quantityStock;
    private int soldQuantity;
    private String status;
    private Timestamp createAt;
    private String brand;
    private int isActive;
    private String categoryName;

    public Product() {
    }

    public Product(String name, int id, double price, int categoryId, int discountDefault, String thumbnail, int quantityStock, int soldQuantity, String status, Timestamp createAt, String brand) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.categoryId = categoryId;
        this.discountDefault = discountDefault;
        this.thumbnail = thumbnail;
        this.quantityStock = quantityStock;
        this.soldQuantity = soldQuantity;
        this.status = status;
        this.createAt = createAt;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPriceAfterDiscount() { // tính giá bán (giá gốc sau khi giảm)
        return price * (100.0 - discountDefault) / 100.0;
    }
    public boolean isOutOfStock() {
        return quantityStock <= 0;
    }
    public String getBadgeType() {
        if (discountDefault > 0) {
            return "SALE";
        }
        if (soldQuantity > 50) {
            return "HOT";
        }
        if (createAt != null) {
            long now = System.currentTimeMillis();
            long diff = now - createAt.getTime(); // millis
            long days = diff / (1000L * 60 * 60 * 24);
            if (days <= 30) {
                return "NEW";
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discountDefault=" + discountDefault +
                ", categoryId=" + categoryId +
                ", thumbnail='" + thumbnail + '\'' +
                ", quantityStock=" + quantityStock +
                ", soldQuantity=" + soldQuantity +
                ", status='" + status + '\'' +
                ", createAt=" + createAt +
                ", brand='" + brand + '\'' +
                '}';
    }
    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
