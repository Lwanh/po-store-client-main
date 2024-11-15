package com.mware.polyshoprestapi.models;

public class Bill {
    private String productId, email, createdAt;
    private int quantity, price;

    public Bill() {
    }

    public Bill(String productId, String email, String createdAt, int quantity, int price) {
        this.productId = productId;
        this.email = email;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
