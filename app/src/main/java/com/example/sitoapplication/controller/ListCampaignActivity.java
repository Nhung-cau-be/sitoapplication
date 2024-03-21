package com.example.sitoapplication.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.model.CampaignViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ListCampaignActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    private ListView listViewCampaign;
    private CampaignArrayAdapter campaignArrayAdapter;
    private MaterialToolbar topAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_campaign);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String name = user.getDisplayName();
            String phone = user.getPhoneNumber();
            Uri url = user.getPhotoUrl();
            String uid = user.getUid();
            String email = user.getEmail();

            System.out.println(name + phone + url +uid + email);
        }


//        listViewCampaign = findViewById(R.id.lvChienDich);
//        topAppBar = findViewById(R.id.listCampaignTopAppBar);
//
//        campaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);
//
//        campaignViewModel.getAll().observe(this, campaigns ->  {
//            // Update the cached copy of the words in the adapter.
//            campaignArrayAdapter = new CampaignArrayAdapter(this, R.layout.list_campaign_item, campaigns);
//            listViewCampaign.setAdapter(campaignArrayAdapter);
//        });
//
//        topAppBar.setOnMenuItemClickListener(item -> {
//            if(item.getItemId() == R.id.lctab_create_campaign) {
//                Intent intent = new Intent(ListCampaignActivity.this, CreateCampaignActivity.class);
//                startActivity(intent);
//
//                return true;
//            }
//
//            return false;
//        });
    }
}
