package com.example.sitoapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        topAppBar = findViewById(R.id.home_top_app_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            topAppBar.getMenu().findItem(R.id.home_top_app_bar_user).setVisible(false);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.home_top_app_bar_user) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.home_top_app_bar_search) {
                Intent intent = new Intent(getApplicationContext(), HomeSearchActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.bnm_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bnm_user) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            }
            if (item.getItemId() == R.id.bnm_map) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            }

            return false;
        });
    }
}
