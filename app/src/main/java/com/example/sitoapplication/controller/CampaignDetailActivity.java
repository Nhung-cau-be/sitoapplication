package com.example.sitoapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.common.DateSupport;
import com.example.sitoapplication.common.NumberSupport;
import com.example.sitoapplication.model.Campaign;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CampaignDetailActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtTarget;
    TextView txtDeadline;
    TextView txtAddress;
    TextView txtStory;
    TextView txtCreator;
    Button btnDonate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_detail);
        String campaignId = String.valueOf(getIntent().getStringExtra("campaign_id"));

        txtName = findViewById(R.id.txtName);
        txtTarget = findViewById(R.id.txtTarget);
        txtDeadline = findViewById(R.id.txtDeadline);
        txtAddress = findViewById(R.id.txtAddress);
        txtStory = findViewById(R.id.txtStory);
        txtCreator = findViewById(R.id.txtCreator);
        btnDonate = findViewById(R.id.buttonDonate);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("campaign").document(campaignId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Campaign campaign = document.toObject(Campaign.class);
                        txtName.setText(campaign != null ? campaign.getName() : "");
                        txtTarget.setText(NumberSupport.getInstance().asCurrency(campaign != null ? campaign.getTarget() : 0));
                        txtDeadline.setText(DateSupport.getInstance().getRemainDays(new Date(), campaign.getDeadline()) + "ngày");
                        txtAddress.setText(campaign != null ? campaign.getAddress() : "");
                        txtStory.setText(campaign != null ? campaign.getStory() : "");

                        String createdUserId = campaign.getCreatedUserId();
                        DocumentReference userRef = db.collection("user").document(createdUserId);
                        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot userDocument = task.getResult();
                                    if (userDocument.exists()) {
                                        User creator = userDocument.toObject(User.class);
                                        txtCreator.setText(creator != null ? creator.getName() : "");
                                    }
                                }
                            }
                        });
                    } else {
                        Log.e("TAG", "No such document");
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignDetailActivity.this, DonateActivity.class);
                intent.putExtra("campaign_id", campaignId);
                startActivity(intent);
            }
        });

    }
}
