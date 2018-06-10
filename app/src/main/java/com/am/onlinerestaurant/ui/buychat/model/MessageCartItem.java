package com.am.onlinerestaurant.ui.buychat.model;

public class MessageCartItem {
    private String name;
    private int quantity;
    private double itemPrice;
    private String message;

    public double getItemPrice() {
        return itemPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
