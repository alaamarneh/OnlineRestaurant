package com.am.onlinerestaurant.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.onlinerestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartViewFragment extends Fragment {


    public CartViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_view, container, false);
    }

}
