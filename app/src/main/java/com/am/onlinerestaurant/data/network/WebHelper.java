package com.am.onlinerestaurant.data.network;

import com.am.onlinerestaurant.data.callback.LiveTask;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;

import java.util.List;

public interface WebHelper {
    LiveTask<List<Restaurant>> getRestaurants();
    LiveTask<List<RestaurantCat>> getRestaurantCategories(String rID);
}
