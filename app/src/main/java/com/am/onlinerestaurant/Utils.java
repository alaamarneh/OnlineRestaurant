package com.am.onlinerestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.am.onlinerestaurant.models.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    public static class SPController{
        public static int getQuantity(Context context, String id){
            SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
            return sp.getInt("id",1);
        }
    }
    public static class CartUtil{
        public static Cart getCart(Context context){
            SharedPreferences sharedPreferences = context.getSharedPreferences("cart",Context.MODE_PRIVATE);
            String cartJson  = sharedPreferences.getString("cartJson",null);
            if(cartJson == null)
                return null;
            Cart cart = new Gson().fromJson(cartJson,Cart.class);
            return cart;
        }
        public static void saveCart(Context context, Cart cart){
            SharedPreferences sp = context.getSharedPreferences("cart",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("cartJson",new Gson().toJson(cart));
            editor.apply();
        }
    }
}
