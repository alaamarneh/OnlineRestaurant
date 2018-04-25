package com.am.onlinerestaurant;

/**
 * Created by ALa on 3/13/2018.
 */

public interface ICallBack<B> {
    void onResponse(B value);
    void onError(String err);
}
