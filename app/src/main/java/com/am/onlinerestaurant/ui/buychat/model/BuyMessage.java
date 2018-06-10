package com.am.onlinerestaurant.ui.buychat.model;

import com.google.firebase.database.ServerValue;

public class BuyMessage {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_CART = 2;
    public static final int TYPE_LOADING = 3;
    public static final int TYPE_ACCEPT= 4;
    public static final int TYPE_REJECT = 5;
    public static final int TYPE_REPORT = 6;
    public static final int TYPE_ITEM_ADDED = 7;
    public static final int TYPE_ITEM_DELETED = 8;
    public static final int TYPE_ITEM_CHANGED = 9;

    public int getType(){return type;}

    protected int type;

    public void setType(int type) {
        this.type = type;
    }
    protected Object date;

    public BuyMessage() {
        date = ServerValue.TIMESTAMP;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
