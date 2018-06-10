package com.am.onlinerestaurant.models;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.widget.Toast;

import com.am.onlinerestaurant.Utils;

import java.util.ArrayList;
import java.util.List;

public class Cart{
    private Restaurant restaurant;
    private List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }
    private static Cart INSTANCE ;
    public Cart() {
    }

    public double getPrice(){
        double sum=0;
        for (CartItem ci :
                cartItems) {
            sum += (ci.getFood().getNewPrice() * ci.getQuantity());
        }
        return Math.round(sum * 100.0)/100.0;
    }
    public int getCount(){
        int sum=0;
        for (CartItem ci :
                cartItems) {
            sum += (ci.getQuantity());
        }
        return sum;
    }
    public void clear(Context context){
        cartItems.clear();
        restaurant = null;
        Utils.CartUtil.saveCart(context,this);
    }

    public synchronized static Cart getCart(Context context){
        Cart cart= Utils.CartUtil.getCart(context);
        if(cart == null)
            return new Cart();
        return cart;
    }
    public boolean isEmpty(){
        return cartItems.isEmpty();
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public int getItemCount(Food food) {
        for (CartItem ci :
                cartItems) {
            if (ci.getFood().getId().equals(food.getId())) {
                return ci.getQuantity();
            }
        }
        return 0;
    }

    public synchronized Cart addItem(Context context, CartItem ci){
        // check if the item belong to same restaurant
        if( Cart.getCart(context).getRestaurant() != null){ // not first add
            String id = Cart.getCart(context).getRestaurant().getId();
            if(!ci.getFood().getRestaurant().getId().equals(id)){
                Toast.makeText(context, "new restaurant, this will clear previous cart", Toast.LENGTH_SHORT).show();
                clear(context);
            }
        }

        Food food = ci.getFood();
       int q = ci.getQuantity();
        boolean found = false;
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getFood().getId().equals(food.getId())){
                // add quantity
                if(cartItem.getMessage() != null && ci.getMessage() != null){
                    if (cartItem.getMessage().equals(ci.getMessage())){
                        // merge
                        cartItem.setQuantity(cartItem.getQuantity() + q);
                        found = true;
                        break;
                    }
                }else if (cartItem.getMessage() == null && ci.getMessage() == null){
                    // merge here also
                    cartItem.setQuantity(cartItem.getQuantity() + q);
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            cartItems.add(ci);
            setRestaurant(ci.getFood().getRestaurant());
        }

        Utils.CartUtil.saveCart(context,this);
        return this;
    }
    public synchronized Cart decrementItem(Context context, Food food) {
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getFood().getId().equals(food.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                if (cartItem.getQuantity() <= 0) {
                    cartItems.remove(cartItem);
                    break;
                }
                break;
            }
        }
        Utils.CartUtil.saveCart(context,this);
        return this;
    }
    public synchronized int decrementItem(Context context, CartItem ci) {
        int c=0;
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getId().equals(ci.getId())) {
                c=cartItem.getQuantity();
                c--;
                cartItem.setQuantity(c);
                if (cartItem.getQuantity() <= 0) {
                    c=0;
                    cartItems.remove(cartItem);
                    break;
                }
                break;
            }
        }
        Utils.CartUtil.saveCart(context,this);
        return c;
    }
    public synchronized int incrementItem(Context context, CartItem ci) {
        int c=0;
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getId().equals(ci.getId())) {
                c = cartItem.getQuantity();
                c++;
                cartItem.setQuantity(c);
                break;
            }
        }
        Utils.CartUtil.saveCart(context,this);
        return c;
    }
    public synchronized Cart incrementItem(Context context, Food food) {
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getFood().getId().equals(food.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                break;
            }
        }
        Utils.CartUtil.saveCart(context,this);
        return this;
    }
    public void removeItem(Context context,CartItem ci){
        for (CartItem cartItem :
                cartItems) {
            if (cartItem.getId().equals(ci.getId())) {
                cartItems.remove(cartItem);
                break;
            }
        }
        Utils.CartUtil.saveCart(context,this);
    }
}
