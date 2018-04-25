package com.am.onlinerestaurant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.am.onlinerestaurant.activities.BuyChatActivity;
import com.am.onlinerestaurant.fragments.mainFragments.HomeFragment;
import com.am.onlinerestaurant.fragments.mainFragments.RestaurantsListFragment;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                replaceFragment(new HomeFragment(),false);
                                break;
                            case R.id.action_explore:
                                replaceFragment(new RestaurantsListFragment(),false);
                                break;
                            case R.id.action_profile:
                                Intent i = new Intent(MainActivity.this, BuyChatActivity.class);
                                startActivity(i);
                                break;
                        }
                        return true;
                    }
                });

        replaceFragment(new HomeFragment(),false);// main fragment
    }

    protected void replaceFragment(Fragment fragment, boolean addToStack){
        mFragment = fragment;
        FragmentManager fm = getSupportFragmentManager();
        if(addToStack)
            fm.beginTransaction().replace(R.id.content,fragment).addToBackStack(null).commit();
        else
            fm.beginTransaction().replace(R.id.content,fragment).commit();
    }

    public void notify(String title,String text,String id)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(text);
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");


        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
        Log.i("tag","mNotificationManagerg");
    }
}