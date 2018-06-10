package com.am.onlinerestaurant.models;

import com.am.onlinerestaurant.data.callback.LiveTask;

public class Category {
    private String id;
    private int drawable;
    private String name;


    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
