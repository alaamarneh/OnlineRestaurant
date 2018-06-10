package com.am.onlinerestaurant.ui.buychat.model;

public class MessageText extends BuyMessage {
    private String text;
    private String uid;

    public MessageText() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
