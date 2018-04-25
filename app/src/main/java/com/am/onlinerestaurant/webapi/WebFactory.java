package com.am.onlinerestaurant.webapi;

public class WebFactory {
    public static WebApi getWebService(){
        return WebService.getInstance();
    }
}
