package com.am.onlinerestaurant.ui.profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.am.onlinerestaurant.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class ProfileFragment extends Fragment {

    private Button btnLogout,btnMyOrders;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnMyOrders = view.findViewById(R.id.btnMyOrders);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v->{
            AuthUI.getInstance().signOut(getContext()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnMyOrders.setOnClickListener(v -> {

        });
        return view;
    }

}
