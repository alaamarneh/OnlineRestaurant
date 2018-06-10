package com.am.onlinerestaurant.fragments.mainFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.activities.RestaurantActivity;
import com.am.onlinerestaurant.data.DataManager;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.webapi.WebApi;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rvRestaurant,rvFood;
    private WebApi mWebApi;
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvRestaurant = view.findViewById(R.id.rvRestaurants);
        rvFood = view.findViewById(R.id.rvFood);

        rvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvFood.setLayoutManager(new GridLayoutManager(getContext(),2));

        mWebApi = WebFactory.getWebService();

        refreshDataFromWeb();
    }

    private void refreshDataFromWeb() {
        DataManager.getWebHelper()
                .getRestaurants()
                .observe(this,listLiveResponse -> {
                    if(listLiveResponse!=null && listLiveResponse.isSuccess()){
                        loadListToRestaurantAdapter(listLiveResponse.data);
                    }
                });
 
        mWebApi.getTopFood(new ICallBack<List<Food>>() {
            @Override
            public void onResponse(List<Food> value) {
                loadListToFoodAdapter(value);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    private void loadListToFoodAdapter(List<Food> value) {
        FoodAdapter adapter = new FoodAdapter(value){
            @Override
            public void onItemClicked(Food item,View view) {
                // TODO: 4/23/2018 onFood clicked
            }
        };
        rvFood.setAdapter(adapter);
    }

    private void loadListToRestaurantAdapter(List<Restaurant> value) {
        RestaurantAdapter adapter = new RestaurantAdapter(value){
            @Override
            public void onItemClicked(Restaurant item,View view) {
                // TODO: 3/15/2018 onRestaurant Clicked
                Intent i = new Intent(getContext(), RestaurantActivity.class);
                i.putExtra("model",item);
                startActivity(i);
            }
        };
        rvRestaurant.setAdapter(adapter);
    }

    private static class RestaurantHolder extends Holder<Restaurant> {
        private TextView tvRestaurantName;
        private ImageView imgRestaurant;
        public RestaurantHolder(View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvName);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
        }

        @Override
        public void bind(Restaurant item, int pos) {
            super.bind(item, pos); // necessary
            tvRestaurantName.setText(item.getName());
            Glide.with(itemView).load(item.getImgUrl()).apply(RequestOptions.circleCropTransform()).into(imgRestaurant);
        }
    }
    private static class RestaurantAdapter extends RecyclerAdapter<Restaurant> {
        public RestaurantAdapter(List<Restaurant> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_restaurant_circular;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new RestaurantHolder(v);
        }
    }


    private static class FoodHolder extends Holder<Food> {
        private TextView tvName,tvPrice,tvRestaurantName;
        private ImageView image;
        public FoodHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
        }

        @Override
        public void bind(Food item, int pos) {
            super.bind(item, pos); // necessary
            tvName.setText(item.getName());
            tvPrice.setText(item.getNewPrice()+"₪");
            tvRestaurantName.setText("At "+item.getRestaurant().getName());
            Glide.with(itemView).load(item.getImgUrl()).into(image);
        }
    }
    private static class FoodAdapter extends RecyclerAdapter<Food> {
        public FoodAdapter(List<Food> items) {
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
