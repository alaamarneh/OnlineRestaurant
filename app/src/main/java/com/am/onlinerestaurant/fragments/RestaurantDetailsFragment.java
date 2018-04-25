package com.am.onlinerestaurant.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.am.onlinerestaurant.abstractAdapters.Holder;
import com.am.onlinerestaurant.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.activities.FoodDetailsActivity;
import com.am.onlinerestaurant.models.Category;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.am.onlinerestaurant.webapi.WebService;
import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantDetailsFragment extends Fragment {
    private RecyclerView recyclerViewTop,recyclerViewMain;
    private Restaurant mRestaurant;

    public RestaurantDetailsFragment() {
    }

    public static RestaurantDetailsFragment getInstance(Restaurant restaurant) {
        RestaurantDetailsFragment fragment = new RestaurantDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model",restaurant);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRestaurant = getArguments().getParcelable("model");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);
    }
    public void setCat(RestaurantCat restaurantCat){
        WebFactory.getWebService().getFood(restaurantCat, new ICallBack<List<Food>>() {
            @Override
            public void onResponse(List<Food> value) {
                MyAdapterMain adapterMain = new MyAdapterMain(value){
                    @Override
                    public void onItemClicked(Food item,View view) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.image), "trImage");

                        Intent i = new Intent(getContext(), FoodDetailsActivity.class);
                        i.putExtra("model",item);
                        startActivity(i,options.toBundle());
                    }
                };
                recyclerViewMain.setAdapter(adapterMain);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewTop = view.findViewById(R.id.recyclerViewTop);
        recyclerViewMain = view.findViewById(R.id.recyclerViewMain);

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(),2));



    }
    class TopHolder extends Holder<RestaurantCat>{
        private ImageView imageView;
        private TextView tvName;

        public TopHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);
        }

        @Override
        public void bind(final RestaurantCat item, int pos) {
            super.bind(item, pos);
            Glide.with(itemView).load(item.getImgUrl()).into(imageView);
            tvName.setText(item.getName());

        }
    }
    class MyAdapterTop extends RecyclerAdapter<RestaurantCat>{

        public MyAdapterTop(List<RestaurantCat> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_restaurant_cat;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new TopHolder(v);
        }
    }

    private class FoodHolder extends Holder<Food> {
        private TextView tvName,tvPrice;
        private ImageView image;
        public FoodHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        @Override
        public void bind(Food item, int pos) {
            super.bind(item, pos); // necessary
            tvName.setText(item.getName());
            tvPrice.setText(item.getNewPrice()+"₪");
            Glide.with(itemView).load(item.getImgUrl()).into(image);
        }
    }

    class MyAdapterMain extends RecyclerAdapter<Food>{

        public MyAdapterMain(List<Food> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_food_without_restaurant;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new FoodHolder(v);
        }
    }
}
