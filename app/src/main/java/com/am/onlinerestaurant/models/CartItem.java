package com.am.onlinerestaurant.models;

import java.util.UUID;

public class CartItem {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Food food;
    private int quantity;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CartItem(Food food, int quantity) {
        setId(UUID.randomUUID().toString());
        this.food = food;
        this.quantity = quantity;
    }

    public CartItem() {
        setId(UUID.randomUUID().toString());
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
