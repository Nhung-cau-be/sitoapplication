package com.example.sitoapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.CampaignViewModel;
import com.example.sitoapplication.repository.database.CampaignDatabase;
import com.example.sitoapplication.repository.database.dao.CampaignDao;
import com.example.sitoapplication.entity.Campaign;

public class CreateCampaignActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    TextView txtName;
    TextView txtTarget;
    TextView txtDeadline;
    TextView txtAddress;
    TextView txtStory;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);

        campaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);

        txtName = findViewById(R.id.editTextName);
        txtTarget = findViewById(R.id.editTextTarget);
        txtDeadline = findViewById(R.id.editTextDeadline);
        txtAddress = findViewById(R.id.editTextAddress);
        txtStory = findViewById(R.id.editTextStory);
        btnCreate = findViewById(R.id.buttonCreate);

        campaignViewModel.getAll().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            if(campaignViewModel.getAll().getValue() != null)
                campaignViewModel.getAll().getValue().forEach(campaign1 -> Log.v("sdasdsadsadas", campaign1.getName()));
            else
                Log.v("Áddddd", "Khong co");
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Campaign campaign = new Campaign();
                campaign.setName(txtName.getText().toString());
                campaign.setTarget(Long.parseLong(txtTarget.getText().toString()));

                campaignViewModel.insert(campaign);

                Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
                intent.putExtra("campaign_id", campaign.getId());
                startActivity(intent);
            }
        });
    }
}
