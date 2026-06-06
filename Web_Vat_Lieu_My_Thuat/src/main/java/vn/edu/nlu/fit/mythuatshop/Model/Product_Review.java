package vn.edu.nlu.fit.mythuatshop.Model;

import java.sql.Timestamp;

public class Product_Review {
    private int id;
    private int userID;
    private int productID;
    private int orderID;
    private int rating;
    private String comment;
    private Timestamp createAt;
    private String username;
    public Product_Review(int id, int userID, int productID, int orderID , int rating, String comment, Timestamp createAt) {
        this.id = id;
        this.userID = userID;
        this.productID = productID;
        this.orderID = orderID;
        this.rating = rating;
        this.comment = comment;
        this.createAt = createAt;
    }

    public Product_Review() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "Product_Review{" +
                "id=" + id +
                ", userID=" + userID +
                ", productID=" + productID +
                ", orderID=" + orderID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
