package com.am.onlinerestaurant.controllers;

import android.content.Context;

import com.am.onlinerestaurant.models.Cart;
import com.am.onlinerestaurant.models.CartItem;

public class CartController {
    public void addItemToCart(Context context, CartItem item){
        Cart cart = Cart.getCart(context);
        cart.addItem(context,item);
    }
}
