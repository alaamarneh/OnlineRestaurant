package com.am.onlinerestaurant.data.callback;

import android.arch.lifecycle.LiveData;

public class LiveTask<T> extends LiveData<LiveResponse<T>> {
    public void success(T data){
        setValue(LiveResponse.success(data));
    }
    public void error(String msg){
        setValue(LiveResponse.error(msg,null));
    }
}
