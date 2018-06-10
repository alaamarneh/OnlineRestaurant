package com.am.onlinerestaurant.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolderFirebase<T> extends RecyclerView.ViewHolder {

    public BaseViewHolderFirebase(View itemView) {
        super(itemView);
    }

    public abstract void onBind(T item, int position);
}
