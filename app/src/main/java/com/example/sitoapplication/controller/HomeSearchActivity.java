package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sitoapplication.R;
import com.example.sitoapplication.adapter.HomeSearchViewPaperAdapter;
import com.example.sitoapplication.model.HomeSearchViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;

public class HomeSearchActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private HomeSearchViewPaperAdapter homeSearchViewPaperAdapter;
    private HomeSearchViewModel homeSearchViewModel;
    private TextInputEditText txtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);

        tabLayout = findViewById(R.id.home_search_tab_layout);
        viewPager = findViewById(R.id.home_search_pager);
        txtSearch = findViewById(R.id.home_search_search_edittext);

        homeSearchViewPaperAdapter = new HomeSearchViewPaperAdapter(this);
        viewPager.setAdapter(homeSearchViewPaperAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if(position == 0)
                        tab.setText("Tài khoản");
                    else
                        tab.setText("Chiến dịch");
                }
        ).attach();

        homeSearchViewModel = new ViewModelProvider(this).get(HomeSearchViewModel.class);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                homeSearchViewModel.setSearchString(s.toString());
            }
        });
    }
}
