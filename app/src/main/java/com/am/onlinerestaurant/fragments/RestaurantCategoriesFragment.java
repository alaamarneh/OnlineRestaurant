package com.am.onlinerestaurant.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.am.onlinerestaurant.webapi.WebApi;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public class RestaurantCategoriesFragment extends ListFragment implements ICallBack<List<RestaurantCat>>{
    private MyAdapter mAdapter;
    private WebApi webService;
    @Override
    public void setupRecyclerViewAdapter() {
        // set empty adapter
        mAdapter = new MyAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        webService = WebFactory.getWebService();
        webService.getRestaurantCategories(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void refreshData() {
        webService.getRestaurantCategories(this);
    }

    // response from web service
    @Override
    public void onResponse(List<RestaurantCat> data) {
        mAdapter.setList(data);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String err) {

    }

    private static class RestaurantCatHolder extends Holder<RestaurantCat> {
        private TextView tvName;
        private ImageView image;
        public RestaurantCatHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);
        }

        @Override
        public void bind(RestaurantCat item, int pos) {
            super.bind(item, pos); // necessary
            tvName.setText(item.getName());
            Glide.with(itemView).load(item.getImgUrl()).into(image);

        }
    }
    private static class MyAdapter extends RecyclerAdapter<RestaurantCat> {
        public MyAdapter(List<RestaurantCat> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_restaurant_cat;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new RestaurantCatHolder(v);
        }
    }
}
