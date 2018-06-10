package com.am.onlinerestaurant.ui.cart;

import android.support.v4.app.Fragment;

import com.am.onlinerestaurant.common.EmptyActivity;

public class CartItemsActivity extends EmptyActivity {
    @Override
    protected String getBarTitle() {
        return "Cart";
    }

    @Override
    public Fragment getFragment() {
        return new CartViewFragment();
    }
}
