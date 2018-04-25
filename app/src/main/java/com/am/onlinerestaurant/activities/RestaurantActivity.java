package com.am.onlinerestaurant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.abstractAdapters.Holder;
import com.am.onlinerestaurant.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.fragments.CartFragment;
import com.am.onlinerestaurant.fragments.RestaurantDetailsFragment;
import com.am.onlinerestaurant.models.Cart;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements CartFragment.OnFragmentInteractionListener{

    private Restaurant mRestaurant;
    private ImageView imageBackground, imageTop;
    private RecyclerView recyclerViewTop;
    private RestaurantDetailsFragment mFragment;
    private TextView tvRestaurantName;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mRestaurant = getIntent().getParcelableExtra("model");
        imageBackground = findViewById(R.id.imageBackground);
        imageTop = findViewById(R.id.imageTop);
        recyclerViewTop = findViewById(R.id.recyclerViewTop);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);


        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mFragment = RestaurantDetailsFragment.getInstance(mRestaurant);
        cartFragment = new CartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentCart, cartFragment)
                .commit();

        Glide.with(this).load(mRestaurant.getImgUrl()).into(imageBackground);
        Glide.with(this).load(mRestaurant.getImgUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imageTop);
        tvRestaurantName.setText(mRestaurant.getName());


        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCart();
    }
    private void refreshCart(){
        if(cartFragment != null)
        cartFragment.refresh();
    }

    private void refreshData() {
        WebFactory.getWebService().getCategories(mRestaurant, new ICallBack<List<RestaurantCat>>() {
            @Override
            public void onResponse(List<RestaurantCat> value) {
                mFragment.setCat(value.get(0));
                MyAdapterTop adapterTop = new MyAdapterTop(value){
                    @Override
                    public void onItemClicked(RestaurantCat item,View view) {
                        mFragment.setCat(item);
                    }
                };
                recyclerViewTop.setAdapter(adapterTop);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    @Override
    public void onCartFragmentClicked() {
        Cart.getCart(this).clear(this);
        cartFragment.refresh();
    }


    class TopHolder extends Holder<RestaurantCat> {
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
    class MyAdapterTop extends RecyclerAdapter<RestaurantCat> {

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
}
