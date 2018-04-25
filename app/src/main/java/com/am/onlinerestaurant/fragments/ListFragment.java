package com.am.onlinerestaurant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.am.onlinerestaurant.R;


public abstract class ListFragment extends Fragment {
    public static final String ARG_NUM_OF_COLS = "numberOfCols";
    public static final String ARG_ITEM = "item";
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public ListFragment() {
    }

    protected void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        int numberOfCols = getNumberOfCols();
        if(numberOfCols == 1)
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfCols));
    }

    protected int getNumberOfCols(){
        if(getArguments() != null && getArguments().containsKey(ARG_NUM_OF_COLS))
        return getArguments().getInt(ARG_NUM_OF_COLS);
        return 1;
    }
    public abstract void setupRecyclerViewAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        initRecyclerView();
        initSwipeToRefresh();
        setupRecyclerViewAdapter();
        return view;
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    protected abstract void refreshData();


}
