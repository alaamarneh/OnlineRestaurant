package com.am.onlinerestaurant.common.abstractAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alaam on 2/1/2018.
 */

public abstract class Holder<T> extends RecyclerView.ViewHolder{
    protected T mItem;

    public Holder(View itemView) {
        super(itemView);
    }
    public void bind(T item, int pos){
        mItem = item;
    }
}