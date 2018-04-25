package com.am.onlinerestaurant.fragments.mainFragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.abstractAdapters.Holder;
import com.am.onlinerestaurant.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.fragments.ListFragment;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.webapi.WebApi;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.am.onlinerestaurant.webapi.WebService;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ALa on 3/13/2018.
 */

public class RestaurantsListFragment extends ListFragment implements
        ICallBack<List<Restaurant>>{
    private MyAdapter mAdapter;
    private WebApi webService;
    @Override
    public void setupRecyclerViewAdapter() {
        // set empty adapter
        mAdapter = new MyAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        webService =  WebFactory.getWebService();
        webService.getRestaurants(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void refreshData() {
        webService.getRestaurants(this);
    }

    // response from web service
    @Override
    public void onResponse(List<Restaurant> data) {
        mAdapter.setList(data);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // optional
    @Override
    protected int getNumberOfCols() {
        return 1;
    }

    @Override
    public void onError(String err) {

    }


    private static class RestaurantHolder extends Holder<Restaurant>{
        private TextView tvRestaurantName;
        private ImageView imgRestaurant;
        public RestaurantHolder(View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
        }

        @Override
        public void bind(Restaurant item, int pos) {
            super.bind(item, pos); // necessary
            tvRestaurantName.setText(item.getName());
            Glide.with(itemView).load(item.getImgUrl()).into(imgRestaurant);
        }
    }
    private static class MyAdapter extends RecyclerAdapter<Restaurant>{
        public MyAdapter(List<Restaurant> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_restaurant;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new RestaurantHolder(v);
        }
    }
}
