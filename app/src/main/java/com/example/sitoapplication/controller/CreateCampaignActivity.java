package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sitoapplication.R;
import com.example.sitoapplication.database.CampaignDatabase;
import com.example.sitoapplication.database.dao.CampaignDao;
import com.example.sitoapplication.model.Campaign;

import java.util.List;

public class CreateCampaignActivity extends AppCompatActivity {
    CampaignDatabase campaignDatabase;
    CampaignDao campaignDao;
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

        txtName = findViewById(R.id.editTextName);
        txtTarget = findViewById(R.id.editTextTarget);
        txtDeadline = findViewById(R.id.editTextDeadline);
        txtAddress = findViewById(R.id.editTextAddress);
        txtStory = findViewById(R.id.editTextStory);
        btnCreate = findViewById(R.id.buttonCreate);

        CampaignDao campaignDao = CampaignDatabase.getDatabase(getApplicationContext()).campaignDao();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                            Campaign campaign = new Campaign();
                            campaign.setName(txtName.getText().toString());
                            campaign.setTarget(Long.parseLong(txtTarget.getText().toString()));

                            campaignDao.insert(campaign);

                        campaignDao.getAll().forEach(campaign1 ->
                                Log.d("ád", campaign1.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

                thread.start();
            }
        });
    }
}
