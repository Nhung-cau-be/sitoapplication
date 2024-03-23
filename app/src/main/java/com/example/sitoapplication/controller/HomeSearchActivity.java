package com.example.sitoapplication.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.google.android.material.tabs.TabLayout;

public class HomeSearchActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);
    }
}
