package vn.edu.nlu.fit.mythuatshop.Model;

public class CartItem {
    private int id;
    private String name;
    private double price;
    private int discountDefault;
    private String thumbnail;
    private int quantity;
    private int stockQuantity;

    public CartItem() {
    }

    public CartItem(int productId, int quantity) {
        this.id = productId;
        this.quantity = quantity;
    }

    public CartItem(int productId, String name, double price, int discountDefault, String thumbnail, int quantity, int stockQuantity) {
        this.id = productId;
        this.name = name;
        this.price = price;
        this.discountDefault = discountDefault;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return id;
    }

    public void setProductId(int productId) {
        this.id = productId;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAfterDiscount() {
        return price * (1 - discountDefault / 100.0);
    }

    public double totalPriceCartItem() {
        return getPriceAfterDiscount() * quantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
