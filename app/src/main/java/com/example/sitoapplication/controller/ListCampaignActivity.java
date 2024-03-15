package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.controller.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.model.CampaignViewModel;

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
            campaignArrayAdapter = new CampaignArrayAdapter(this, R.layout.list_campaign_item, campaigns);
            listViewCampaign.setAdapter(campaignArrayAdapter);
        });
    }
}
