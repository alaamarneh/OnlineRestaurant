package com.am.onlinerestaurant.data.network;

import com.am.onlinerestaurant.data.callback.LiveTask;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppWebHelper implements WebHelper {
    @Override
    public LiveTask<List<Restaurant>> getRestaurants() {
        LiveTask<List<Restaurant>> taskLiveTask = new LiveTask<>();
        DatabaseReference mData = FirebaseEndPoint.RESTAURANTS;
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                List<Restaurant> restaurants = new ArrayList<>();
                while (iterator.hasNext()){
                    DataSnapshot ds= iterator.next();
                    Restaurant restaurant = ds.getValue(Restaurant.class);
                    restaurant.setId(ds.getKey());
                    restaurants.add(restaurant);
                }
                taskLiveTask.success(restaurants);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                taskLiveTask.error(databaseError.getMessage());
            }
        });

        return taskLiveTask;
    }

    @Override
    public LiveTask<List<RestaurantCat>> getRestaurantCategories(String rID) {
        LiveTask<List<RestaurantCat>> task = new LiveTask<>();
        DatabaseReference ref = FirebaseEndPoint.CATEGORIES;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                List<RestaurantCat> cats = new ArrayList<>();
                while (iterator.hasNext()){
                    DataSnapshot ds= iterator.next();
                    RestaurantCat cat = ds.getValue(RestaurantCat.class);
                    cat.setId(ds.getKey());
                    cats.add(cat);
                }
                task.success(cats);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                task.error(databaseError.getMessage());
            }
        });
        return task;
    }
}
