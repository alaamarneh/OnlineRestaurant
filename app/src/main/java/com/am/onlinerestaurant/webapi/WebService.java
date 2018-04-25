package com.am.onlinerestaurant.webapi;

import android.content.Context;
import android.os.Handler;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.models.Category;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public class WebService implements WebApi {
    private static WebService instance;
    private Context context;

    public WebService(Context context) {
        this.context = context;
    }

    public static void init(Context context){
        if(instance == null)
            instance = new WebService(context);
    }
    protected static WebService getInstance(){
        return instance;
    }
    @Override
    public void getRestaurants(final ICallBack<List<Restaurant>> callBack) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Restaurant r1 = new Restaurant();
                r1.setName("Havana");r1.setImgUrl("https://scontent.fjrs3-1.fna.fbcdn.net/v/t1.0-9/22154518_1451020685013107_8433095934352618647_n.jpg?oh=f444418b75d891e9a466d903a1077dd2&oe=5B019A3D");
                r1.setImgLargeUrl("https://scontent.fjrs3-1.fna.fbcdn.net/v/t1.0-9/23319315_1484676401647535_7035311270002603316_n.jpg?oh=6f8d238ecfc9e97b1971618dc57a80b0&oe=5B3B4A13");
                Restaurant r2= new Restaurant();
                 r2.setName("Zorba");r2.setImgUrl("https://scontent.fjrs3-1.fna.fbcdn.net/v/t1.0-9/26113733_754845364716533_4475124620418595203_n.jpg?oh=1ef841f50ba93773c67287f3515de4e5&oe=5B3489FD");
                r2.setImgLargeUrl("https://scontent.fjrs3-1.fna.fbcdn.net/v/t1.0-9/26112008_754844828049920_7517435969782167789_n.jpg?oh=9997b6c53dafa1257b0222d5fb737ecb&oe=5B34718B");
                ArrayList<Restaurant> list = new ArrayList<>();
                list.add(r1);list.add(r2);
                list.add(r1);list.add(r2);
                list.add(r1);list.add(r2);
                callBack.onResponse(list);
            }
        },500);
    }

    @Override
    public void getRestaurantDetail(ICallBack<Restaurant> callBack) {

    }

    @Override
    public void getRestaurantCategories(ICallBack<List<RestaurantCat>> callBack) {

    }

    @Override
    public void getFood(RestaurantCat cat, ICallBack<List<Food>> callBack) {
        getTopFood(callBack);
    }

    @Override
    public void getFoodByCategory(RestaurantCat cat, Category category, ICallBack<List<Food>> callBack) {

    }

    @Override
    public void getCategories(Restaurant restaurant, ICallBack<List<RestaurantCat>> callBack) {
        ArrayList<RestaurantCat> list = new ArrayList<>();
        RestaurantCat category = new RestaurantCat();
        category.setImgUrl("http://icons.iconarchive.com/icons/sonya/swarm/256/Fast-Food-icon.png");
        category.setName("Fast Food");
        category.setRestaurant(restaurant);

        RestaurantCat category2 = new RestaurantCat();
        category2.setImgUrl("http://icons.iconarchive.com/icons/sonya/swarm/256/Turkey-icon.png");
        category2.setName("Meal");
        category2.setRestaurant(restaurant);

        list.add(category);
        list.add(category2);
        list.add(category);
        list.add(category2);
        list.add(category);
        list.add(category2);

        callBack.onResponse(list);
    }

    @Override
    public void getTopFood(final ICallBack<List<Food>> callBack) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Food> list = new ArrayList<>();
                Food food = new Food();food.setId("f0");
                food.setImgUrl("https://scontent.fjrs3-1.fna.fbcdn.net/v/t1.0-9/22814172_727304880803915_5650130441670235108_n.jpg?oh=4f251351dae6658ae6864ef46f46ccc5&oe=5B310B52");
                food.setName("Food1");
                food.setDescription("Fooooood 1");
                food.setNewPrice(15.99f);
                Restaurant restaurant1 = new Restaurant();restaurant1.setName("Havana");
                food.setRestaurant(restaurant1);

                Food food1 = new Food();food1.setId("f1");
                food1.setImgUrl("https://images.theconversation.com/files/180401/original/file-20170731-22175-67v3q2.jpg?ixlib=rb-1.1.0&rect=0%2C589%2C5472%2C2654&q=45&auto=format&w=1356&h=668&fit=crop");
                food1.setName("Cake");
                food1.setDescription("Caaaake");
                food1.setNewPrice(19.99f);
                Restaurant restaurant2 = new Restaurant();restaurant2.setName("Zorba");restaurant1.setId("r1");restaurant2.setId("r2");
                food1.setRestaurant(restaurant2);


                Food food2 = new Food();food2.setId("f2");

                food2.setImgUrl("http://www.alghad.com/files/474552.jpg");
                food2.setName("Cake");
                food2.setDescription("Caaaake");
                food2.setNewPrice(14.99f);
                Restaurant restaurant3 = new Restaurant();restaurant3.setName("MarketPlace");restaurant3.setId("r3");
                food2.setDescription("cake, brown, milk, perfect taste");
                food2.setRestaurant(restaurant3);
                food2.addCat(food1);
                food2.addCat(food);
                food2.addCat(food1);
                food2.addCat(food);


                list.add(food);
                list.add(food1);
                list.add(food1);
                list.add(food);
                list.add(food1);
                list.add(food);
                list.add(food2);
                list.add(food1);
                list.add(food1);
                list.add(food2);

                callBack.onResponse(list);
            }
        },500);
    }
}
