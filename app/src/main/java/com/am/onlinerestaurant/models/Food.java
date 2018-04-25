package com.am.onlinerestaurant.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public class Food implements Parcelable{
    private String name,description,imgUrl;
    private Restaurant restaurant;
    private List<Food> foodCats;
    private String id;

    public Food(){}


    protected Food(Parcel in) {
        name = in.readString();
        description = in.readString();
        imgUrl = in.readString();
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        foodCats = in.createTypedArrayList(Food.CREATOR);
        id = in.readString();
        prevPrice = in.readFloat();
        newPrice = in.readFloat();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public List<Food> getFoodCats() {
        return foodCats;
    }

    public void setFoodCats(List<Food> foodCats) {
        this.foodCats = foodCats;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    private float prevPrice,newPrice;// previous price can be null, we look at the new price

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(float prevPrice) {
        this.prevPrice = prevPrice;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }




    public void addCat(Food food){
        if(foodCats == null)
            foodCats = new ArrayList<>();
        foodCats.add(food);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imgUrl);
        dest.writeParcelable(restaurant, flags);
        dest.writeTypedList(foodCats);
        dest.writeString(id);
        dest.writeFloat(prevPrice);
        dest.writeFloat(newPrice);
    }
}
