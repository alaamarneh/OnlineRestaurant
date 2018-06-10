package com.am.onlinerestaurant.ui.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.am.onlinerestaurant.R;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.kennyc.bottomsheet.menu.BottomSheetMenu;

public abstract class BaseActivity extends AppCompatActivity{
    public void bottomSheet(Context context, String title, BottomSheetMenu sheetMenu, BottomSheetListener sheetListener) {
        new BottomSheet.Builder(context)
                .setMenu(sheetMenu)
                .setTitle(title)
                .setListener(sheetListener)
                .show();
    }
}
