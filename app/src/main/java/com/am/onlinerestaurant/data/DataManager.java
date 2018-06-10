package com.am.onlinerestaurant.data;

import com.am.onlinerestaurant.data.network.AppWebHelper;
import com.am.onlinerestaurant.data.network.WebHelper;

public class DataManager {
    public static WebHelper getWebHelper(){return new AppWebHelper();}
}
