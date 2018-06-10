package com.am.onlinerestaurant.ui.buychat.model;

import java.util.List;

public class MessageCart extends BuyMessage {
    private List<MessageCartItem> items;

    public List<MessageCartItem> getItems() {
        return items;
    }

    public void setItems(List<MessageCartItem> items) {
        this.items = items;
    }

}
