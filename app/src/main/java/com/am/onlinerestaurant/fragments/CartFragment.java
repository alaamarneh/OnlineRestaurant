package com.am.onlinerestaurant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.models.Cart;

public class CartFragment extends Fragment {
    private TextView tvItems,tvCartPrice;
    private View layoutCart;

    private OnFragmentInteractionListener mListener;

    public CartFragment() {
    }
    public void refresh(){
        Cart cart = Cart.getCart(getContext());
        if(cart.isEmpty()){
            layoutCart.setVisibility(View.GONE);
        }else{
            layoutCart.setVisibility(View.VISIBLE);
            int count = cart.getCount();
            double price = cart.getPrice();
            tvItems.setText(count + " Items");
            tvCartPrice.setText(price+" NIS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvItems = view.findViewById(R.id.tvItems);
        tvCartPrice = view.findViewById(R.id.tvCartPrice);
        layoutCart = view.findViewById(R.id.layoutCart);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onCartFragmentClicked();
            }
        });

        refresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onCartFragmentClicked();
    }
}
