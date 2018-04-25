package com.am.onlinerestaurant.activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.am.onlinerestaurant.R;

public class BuyChatActivity extends AppCompatActivity {
    private View layoutMessage, layoutItems;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_chat);

        //binding
        layoutMessage = findViewById(R.id.layoutMessage);
        layoutItems = findViewById(R.id.layoutItems);
        tabLayout = findViewById(R.id.tabLayout);


        TabLayout.Tab tab  = tabLayout.newTab();
        tab.setText("write a message");tab.setTag("message");

        TabLayout.Tab tab2  = tabLayout.newTab();
        tab2.setText("send items");tab2.setTag("items");
        tabLayout.addTab(tab);
        tabLayout.addTab(tab2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getTag().equals("message")){
                    layoutItems.setVisibility(View.GONE);
                    layoutMessage.setVisibility(View.VISIBLE);
                }else{
                    layoutItems.setVisibility(View.VISIBLE);
                    layoutMessage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
