package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.controller.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.entity.Campaign;
import com.example.sitoapplication.model.CampaignViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListCampaignActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    private ListView listViewCampaign;
    private CampaignArrayAdapter campaignArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_campaign);

        campaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);

        listViewCampaign = findViewById(R.id.lvChienDich);
        campaignViewModel.getAll().observe(this, campaigns ->  {
            // Update the cached copy of the words in the adapter.
            campaignArrayAdapter = new CampaignArrayAdapter(this, R.layout.item, campaigns);
            listViewCampaign.setAdapter(campaignArrayAdapter);
        });
    }
}
