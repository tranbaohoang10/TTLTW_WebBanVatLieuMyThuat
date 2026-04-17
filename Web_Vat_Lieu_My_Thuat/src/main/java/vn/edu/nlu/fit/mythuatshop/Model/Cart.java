package vn.edu.nlu.fit.mythuatshop.Model;

import vn.edu.nlu.fit.mythuatshop.Service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> carts;
    private int fee = 0;
    private double discount = 0;
    private Integer voucherId;
    private Long expectedDeliveryTime;
    private String expectedDeliveryDateText;
    private double productDiscount;
    private double shippingDiscount;

    public Cart() {
        carts = new HashMap<>();
    }

    public Map<Integer, CartItem> getCarts() {
        return carts;
    }

    public void setCarts(Map<Integer, CartItem> carts) {
        this.carts = carts;
    }

    public int getFee() {
        return fee;
    }
    public double getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(double productDiscount) {
        this.productDiscount = productDiscount;
    }

    public double getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(double shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.productDiscount = discount;
        this.shippingDiscount = 0;
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

    public void clearShippingInformation() {
        this.fee = 0;
        this.expectedDeliveryTime = null;
        this.expectedDeliveryDateText = null;
    }

    public void addCartItem(CartItem newItem) {
        int productId = newItem.getProductId();
        if (carts.containsKey(productId)) {
            CartItem item = carts.get(productId);
            int newQuantity = item.getQuantity() + newItem.getQuantity();
            if (newQuantity > newItem.getStockQuantity()) {
                newQuantity = newItem.getStockQuantity();
            }
            item.setQuantity(newQuantity);
            item.setStockQuantity(newItem.getStockQuantity());
        } else {
            if (newItem.getQuantity() > newItem.getStockQuantity()) {
                newItem.setQuantity(newItem.getStockQuantity());
            }
            carts.put(productId, newItem);
        }
    }


    public void updateQuantity(int productId, int quantity) {
        if (carts != null && carts.containsKey(productId)) {
            CartItem item = carts.get(productId);
            item.setQuantity(quantity);
        }
    }

    public void removeCartItem(int productId) {
        if (carts != null && carts.containsKey(productId)) {
            carts.remove(productId);
        }
    }

    public int cartSize() {
        return carts != null ? carts.size() : 0;
    }

    public int getTotalQuantity() {
        if (carts == null) {
            return 0;
        }

        int total = 0;
        for (CartItem item : carts.values()) {
            total += item.getQuantity();
        }

        return total;
    }
    public double getTotalProductPrice() {
        double sum = 0;
        for (CartItem item : carts.values()) {
            sum += item.totalPriceCartItem();
        }
        return sum;
    }
    public Integer getVoucherId() { return voucherId; }
    public void setVoucherId(Integer voucherId) { this.voucherId = voucherId; }

    public void clearVoucher() {
        this.productDiscount = 0;
        this.shippingDiscount = 0;
        this.discount = 0;
    }
    public double getTotalPriceToPay() {
        double totalProductPrice = getTotalProductPrice();
        double totalToPay = totalProductPrice - productDiscount + fee - shippingDiscount;

        if (totalToPay < 0) {
            totalToPay = 0;
        }

        return totalToPay;
    }
    public Cart getCartByIds(String[] productIds) {
        Cart cartTemp = new Cart();

        if (productIds == null) {
            return cartTemp;
        }

        for (String id : productIds) {
            try {
                int productId = Integer.parseInt(id);
                CartItem item = carts.get(productId);

                if (item != null) {
                    CartItem itemNew = new CartItem(item.getProductId(), item.getName(), item.getPrice(), item.getDiscountDefault(), item.getThumbnail(), item.getQuantity());
                    itemNew.setStockQuantity(item.getStockQuantity());
                    cartTemp.getCarts().put(productId, itemNew);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cartTemp;
    }
    public void removeCartTemp(Cart cartTemp) {
        if (cartTemp == null) {
            return;
        }

        for (Integer id : cartTemp.getCarts().keySet()) {
            carts.remove(id);
        }
    }
    public List<String> removeOutOfStockItems(ProductService productService) {
        List<String> removedNames = new ArrayList<>(); // de thong bao
        List<Integer> idsToRemove = new ArrayList<>();

        for (Map.Entry<Integer, CartItem> entry : carts.entrySet()) {
            CartItem item = entry.getValue();
            Product product = productService.getProductByIdActive(item.getProductId());

            if (product == null || product.getQuantityStock() <= 0) {
                removedNames.add(item.getName());
                idsToRemove.add(entry.getKey());
            } else {
                item.setStockQuantity(product.getQuantityStock());
            }
        }

        for (Integer id : idsToRemove) {
            carts.remove(id);
        }

        return removedNames;
    }
}
