package com.am.onlinerestaurant.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ALa on 3/13/2018.
 */

public class Restaurant implements Parcelable{
    private String name;
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    protected Restaurant(Parcel in) {
        name = in.readString();
        imgUrl = in.readString();
        ImgLargeUrl = in.readString();
        id = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getImgLargeUrl() {
        return ImgLargeUrl;
    }

    public void setImgLargeUrl(String imgLargeUrl) {
        ImgLargeUrl = imgLargeUrl;
    }

    private String ImgLargeUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Restaurant() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeString(ImgLargeUrl);
        dest.writeString(id);
    }
}
