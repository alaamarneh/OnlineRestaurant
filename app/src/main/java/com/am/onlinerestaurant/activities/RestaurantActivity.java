package com.am.onlinerestaurant.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.ICallBack;
import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.data.DataManager;
import com.am.onlinerestaurant.ui.base.BaseActivity;
import com.am.onlinerestaurant.ui.other.CartFragment;
import com.am.onlinerestaurant.fragments.RestaurantDetailsFragment;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.models.RestaurantCat;
import com.am.onlinerestaurant.ui.cart.CartItemsActivity;
import com.am.onlinerestaurant.webapi.WebFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.kennyc.bottomsheet.menu.BottomSheetMenu;

import java.util.List;

public class RestaurantActivity extends BaseActivity implements CartFragment.OnFragmentInteractionListener{

    private Restaurant mRestaurant;
    private ImageView imageBackground, imageTop;
    private RecyclerView recyclerViewTop;
    private RestaurantDetailsFragment mFragment;
    private TextView tvRestaurantName;
    private CartFragment cartFragment;
    private View btnMenu;
    private List<RestaurantCat> restaurantCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mRestaurant = getIntent().getParcelableExtra("model");
        imageBackground = findViewById(R.id.imageBackground);
        imageTop = findViewById(R.id.imageTop);
        recyclerViewTop = findViewById(R.id.recyclerViewTop);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        btnMenu= findViewById(R.id.btnMenu);


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

        btnMenu.setOnClickListener(view -> {
            showMenu();

        });

        refreshData();
    }

    private void showMenu() {
        BottomSheetMenu menu = new BottomSheetMenu(this);
        for (int i =0;i<restaurantCats.size();i++)
            menu.add(1,i,i,restaurantCats.get(i).getName());

        bottomSheet(this, "Menu", menu, new BottomSheetListener() {
            @Override
            public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {

            }

            @Override
            public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {
                mFragment.setCat(restaurantCats.get(menuItem.getItemId()));
            }

            @Override
            public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

            }
        });
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
        DataManager.getWebHelper()
                .getRestaurantCategories(mRestaurant.getId())
                .observe(this,listLiveResponse -> {
                    if(listLiveResponse!=null&&listLiveResponse.isSuccess()){
                        mFragment.setCat(listLiveResponse.data.get(0));
                        restaurantCats = listLiveResponse.data;
                    }
                });
        /*
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
        */
    }

    @Override
    public void onCartFragmentClicked() {
//        Cart.getCart(this).clear(this);
//        cartFragment.refresh();
        startActivity(new Intent(this, CartItemsActivity.class));
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
