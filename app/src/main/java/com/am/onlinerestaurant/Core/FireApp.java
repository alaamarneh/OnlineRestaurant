package com.am.onlinerestaurant.Core;

import android.app.Application;

import com.am.onlinerestaurant.webapi.WebService;

/**
 * Created by ALa on 3/13/2018.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebService.init(getApplicationContext());
    }
}
