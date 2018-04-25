package com.am.onlinerestaurant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.Utils;
import com.am.onlinerestaurant.abstractAdapters.Holder;
import com.am.onlinerestaurant.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.fragments.CartFragment;
import com.am.onlinerestaurant.models.Cart;
import com.am.onlinerestaurant.models.CartItem;
import com.am.onlinerestaurant.models.Food;
import com.am.onlinerestaurant.models.FoodCat;
import com.bumptech.glide.Glide;

import java.util.List;

public class FoodDetailsActivity extends AppCompatActivity implements CartFragment.OnFragmentInteractionListener{
    private ImageView imageBackground;
    private TextView tvPrice,tvName,tvDescription,tvQuantity;
    private Button btnInc,btnDec,btnAddToCart;
    private RecyclerView recyclerViewCat;
    private View layoutQuantity;

    private CartFragment cartFragment;
    private Food mFood;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        mFood = getIntent().getParcelableExtra("model");

        imageBackground = findViewById(R.id.imageBackground);
        tvPrice = findViewById(R.id.tvPrice);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnInc = findViewById(R.id.btnInc);
        btnDec = findViewById(R.id.btnDec);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        recyclerViewCat = findViewById(R.id.recyclerViewCat);
        layoutQuantity = findViewById(R.id.layoutQuantity);

        cartFragment = new CartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentCart, cartFragment)
                .commit();

        recyclerViewCat.setLayoutManager(new LinearLayoutManager(this));


        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = Cart.getCart(FoodDetailsActivity.this)
                        .addItem(FoodDetailsActivity.this,getCartItem());
                refreshCart();
                tvQuantity.setText(cart.getItemCount(mFood) +"");

                v.setVisibility(View.GONE);
                layoutQuantity.setVisibility(View.VISIBLE);
            }
        });

        refreshData();



    }

    private CartItem getCartItem() {
//        int q = Integer.parseInt(tvQuantity.getText().toString());
        CartItem ci = new CartItem(mFood,1);
        return ci;
    }

    private void decrement() {
        Cart cart = Cart.getCart(this).decrementItem(this,mFood);
        updateUI(cart);
        refreshCart();

    }

    private void increment() {
        Cart cart = Cart.getCart(this).incrementItem(this,mFood);
        updateUI(cart);
        refreshCart();
    }

    private void refreshData() {
        Glide.with(this).load(mFood.getImgUrl()).into(imageBackground);
        tvName.setText(mFood.getName());
        tvPrice.setText(mFood.getNewPrice() + "NIS");
        tvDescription.setText(mFood.getDescription());

        if(mFood.getFoodCats() == null){
            btnAddToCart.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.VISIBLE);
            Log.d("tag","mFood.getFoodCats() = null");
            recyclerViewCat.setVisibility(View.GONE);

            Cart cart = Cart.getCart(this);
            updateUI(cart);
        }else{
            Log.d("tag","mFood.getFoodCats() != null");
            btnAddToCart.setVisibility(View.GONE);
            tvPrice.setVisibility(View.GONE);
            layoutQuantity.setVisibility(View.GONE);
            recyclerViewCat.setVisibility(View.VISIBLE);
            mAdapter = new MyAdapter(mFood.getFoodCats());
            recyclerViewCat.setAdapter(mAdapter);
        }

    }

    private void updateUI(Cart cart) {
        if(mFood.getFoodCats() != null)
                return;

        int q = cart.getItemCount(mFood);
        tvQuantity.setText(q+"");
        if(q <= 0){
            btnAddToCart.setVisibility(View.VISIBLE);
            layoutQuantity.setVisibility(View.GONE);
        }else{
            btnAddToCart.setVisibility(View.GONE);
            layoutQuantity.setVisibility(View.VISIBLE);
        }
        tvQuantity.setText(q+"");
    }

    private void refreshCart(){
        cartFragment.refresh();

    }

    @Override
    public void onCartFragmentClicked() {

    }

    class FoodCatHolder extends Holder<Food>{
        private TextView tvName,tvPrice,tvQuantity;
        private Button btnInc,btnDec,btnAddToCart;
        private View layoutQuantity2;
        FoodCatHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
            btnInc=itemView.findViewById(R.id.btnInc);
            btnDec=itemView.findViewById(R.id.btnDec);
            btnAddToCart=itemView.findViewById(R.id.btnAddToCart);
            layoutQuantity2=itemView.findViewById(R.id.layoutQuantity2);
        }

        @Override
        public void bind(final Food item, int pos) {
            super.bind(item, pos);
            tvName.setText(item.getName());
            tvPrice.setText(item.getNewPrice()+"NIS");
            updateQuantity(Cart.getCart(itemView.getContext()));

            btnInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increment();
                }
            });
            btnDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decrement();
                }
            });
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = Cart.getCart(itemView.getContext())
                            .addItem(itemView.getContext(),new CartItem(item,1));
                    updateQuantity(cart);
                    refreshCart();
                }
            });
        }
        private void updateQuantity(Cart cart){
            int q  = cart.getItemCount(mItem);
            if(q <= 0){
                layoutQuantity2.setVisibility(View.GONE);
                btnAddToCart.setVisibility(View.VISIBLE);
            }else{
                layoutQuantity2.setVisibility(View.VISIBLE);
                btnAddToCart.setVisibility(View.GONE);
            }
            tvQuantity.setText(q+"");
        }

        private void decrement() {
            Cart cart = Cart.getCart(itemView.getContext()).decrementItem(itemView.getContext(),mItem);
            updateQuantity(cart);
            refreshCart();
        }

        private void increment() {
            Cart cart  = Cart.getCart(itemView.getContext()).incrementItem(itemView.getContext(),mItem);
            updateQuantity(cart);
            refreshCart();
        }
    }
    class MyAdapter extends RecyclerAdapter<Food>{

        MyAdapter(List<Food> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_food_cat;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new FoodCatHolder(v);
        }
    }

}
