package com.am.onlinerestaurant.webapi;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.models.Category;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;

import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public interface WebApi {
    String ApiUrl = "";
    void getRestaurants(ICallBack<List<Restaurant>> callBack);
    void getRestaurantDetail(ICallBack<Restaurant> callBack);
    void getRestaurantCategories(ICallBack<List<RestaurantCat>> callBack);
    void getFood(RestaurantCat cat,ICallBack<List<Food>>callBack);
    void getFoodByCategory(RestaurantCat cat,Category category,ICallBack<List<Food>>callBack);
    void getCategories(Restaurant restaurant, ICallBack<List<RestaurantCat>> callBack);
    void getTopFood(ICallBack<List<Food>> callBack);
}
