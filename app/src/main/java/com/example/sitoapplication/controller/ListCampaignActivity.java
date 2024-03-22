package com.example.sitoapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.database.entity.Campaign;
import com.example.sitoapplication.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.model.Campaign;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListCampaignActivity extends AppCompatActivity {
    private ListView listViewCampaign;
    private CampaignArrayAdapter campaignArrayAdapter;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_campaign);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        listViewCampaign = findViewById(R.id.lvChienDich);
        topAppBar = findViewById(R.id.listCampaignTopAppBar);

        db.collection("campaign")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Campaign> campaigns = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                campaigns.add(document.toObject(Campaign.class));
                            }
                            campaignArrayAdapter = new CampaignArrayAdapter(ListCampaignActivity.this, R.layout.list_campaign_item, campaigns);
                            listViewCampaign.setAdapter(campaignArrayAdapter);
                        } else {
                            Log.d("ListCampaignActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });

        listViewCampaign.setOnItemClickListener((parent, view, position, id) -> {
            Campaign selectedCampaign = campaignArrayAdapter.getItem(position);
            if (selectedCampaign != null) {
                long campaignId = (selectedCampaign.getId());
                Intent intent = new Intent(ListCampaignActivity.this, CampaignDetailActivity.class);
                intent.putExtra("campaign_id", campaignId);
                startActivity(intent);
            }
        });

        topAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.lctab_create_campaign) {
                Intent intent = new Intent(ListCampaignActivity.this, CreateCampaignActivity.class);
                startActivity(intent);

                return true;
            }

            return false;
        });
    }


}
