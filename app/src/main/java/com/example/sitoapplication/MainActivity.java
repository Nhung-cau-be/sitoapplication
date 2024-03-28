package com.example.sitoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sitoapplication.controller.CampaignDetailActivity;
import com.example.sitoapplication.controller.CreateCampaignActivity;
import com.example.sitoapplication.controller.HomeActivity;
import com.example.sitoapplication.controller.InformationActivity;
import com.example.sitoapplication.controller.ListCampaignActivity;
import com.example.sitoapplication.controller.SignInActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, CreateCampaignActivity.class);
        startActivity(intent);
    }
}