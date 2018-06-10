package com.am.onlinerestaurant.data.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseEndPoint {
    public static DatabaseReference getChatRef(String restaurantId, String userId){
        return FirebaseDatabase.getInstance().getReference().child("chat").child(restaurantId+userId);
    }
    public final static DatabaseReference RESTAURANTS = FirebaseDatabase.getInstance().getReference().child("restaurants");
    public final static DatabaseReference CATEGORIES = FirebaseDatabase.getInstance().getReference().child("restaurant_cat");


}
