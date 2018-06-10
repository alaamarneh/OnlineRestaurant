package com.am.onlinerestaurant.ui.cart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.adapters.CartItemsAdapter;
import com.am.onlinerestaurant.models.Cart;
import com.am.onlinerestaurant.ui.buychat.ChatActivity;

public class CartViewFragment extends Fragment implements CartItemsAdapter.CartAdapterListener{

    private RecyclerView recyclerView;
    private Button btnAddItems, btnSendOrder;
    private CartItemsAdapter mAdapter;
    private TextView tvTotal;
    public CartViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        btnAddItems = view.findViewById(R.id.btnAddItems);
        btnSendOrder = view.findViewById(R.id.btnSendOrder);
        tvTotal = view.findViewById(R.id.tvTotal);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CartItemsAdapter(Cart.getCart(getContext()).getCartItems(),this);
        recyclerView.setAdapter(mAdapter);

        //update ui
        onCartItemsChanged();

        btnSendOrder.setOnClickListener(view1 -> {
            Intent i = ChatActivity.getAndSendCart(getContext(),Cart.getCart(getContext()).getRestaurant());
            startActivity(i);
        });
        btnAddItems.setOnClickListener(view1 -> {

        });
    }

    @Override
    public void onCartItemsChanged() {
        Cart cart = Cart.getCart(getContext());

        tvTotal.setText(cart.getPrice()+"NIS");
    }
}
