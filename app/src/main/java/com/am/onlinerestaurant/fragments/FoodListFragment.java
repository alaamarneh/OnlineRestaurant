package com.am.onlinerestaurant.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.am.onlinerestaurant.webapi.WebApi;
import com.am.onlinerestaurant.webapi.WebFactory;

import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public class FoodListFragment extends ListFragment implements ICallBack<List<Food>>{
    private MyAdapter mAdapter;
    private WebApi webService;
    private RestaurantCat mRestaurantCat;
    @Override
    public void setupRecyclerViewAdapter() {
        // set empty adapter
        mAdapter = new MyAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        // TODO: 3/13/2018 get Restaurant Cat here
        webService = WebFactory.getWebService();
        webService.getFood(mRestaurantCat,this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void refreshData() {
        webService.getFood(mRestaurantCat,this);
    }

    // response from web service
    @Override
    public void onResponse(List<Food> data) {
        mAdapter.setList(data);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String err) {

    }


    private static class FoodHolder extends Holder<Food> {
        private TextView tvName;
        private ImageView image;
        public FoodHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);
        }

        @Override
        public void bind(Food item, int pos) {
            super.bind(item, pos); // necessary
            tvName.setText(item.getName());

        }
    }
    private static class MyAdapter extends RecyclerAdapter<Food> {
        public MyAdapter(List<Food> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_food;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new FoodHolder(v);
        }
    }
}
