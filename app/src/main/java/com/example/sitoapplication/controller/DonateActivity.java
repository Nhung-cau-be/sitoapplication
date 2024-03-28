package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.common.NumberSupport;
import com.example.sitoapplication.model.Campaign;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
public class DonateActivity extends AppCompatActivity {

    TextView txtName;
    ImageView imgCampaign;
    TextView txtDonate;
    Button btn5, btn10, btn50, btn100, btn200, btn500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate);
        String campaignId = String.valueOf(getIntent().getStringExtra("campaign_id"));

        //txtName = findViewById(R.id.txtName);
//        imgCampaign = findViewById(R.id.imgCampaign);
        txtDonate = findViewById(R.id.editTextDonate);
        btn5 = findViewById(R.id.button5);
        btn10 = findViewById(R.id.button10);
        btn50 = findViewById(R.id.button50);
        btn100 = findViewById(R.id.button100);
        btn200 = findViewById(R.id.button200);
        btn500 = findViewById(R.id.button500);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("campaign").document(campaignId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Campaign campaign = document.toObject(Campaign.class);
                       // txtName.setText(campaign != null ? campaign.getName() : "");
                    } else {
                        Log.e("TAG", "No such document");
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn5);
                updateDonateAmount(5000);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn10);
                updateDonateAmount(10000);
            }
        });
        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn50);
                updateDonateAmount(50000);
            }
        });
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn100);
                updateDonateAmount(100000);
            }
        });
        btn200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn200);
                updateDonateAmount(200000);
            }
        });
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonColor(btn500);
                updateDonateAmount(500000);
            }
        });
    }
    private void updateDonateAmount(int amount) {
        EditText donateEditText = findViewById(R.id.editTextTarget);
        donateEditText.setText(NumberSupport.getInstance().asCurrencyForDonate(amount));
    }

    private void updateButtonColor(Button selectedButton) {
        Button[] buttons = {btn5, btn10, btn50, btn100, btn200, btn500};
        for (Button button : buttons) {
            button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            button.setTextColor(getResources().getColor(android.R.color.black));
        }
        selectedButton.setBackgroundColor(getResources().getColor(R.color.orange));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
    }

    private boolean validCampaign() {
        try {
            if (txtDonate.getText().toString().isEmpty()) {
                txtDonate.setError("Vui lòng nhập số tiền muốn ủng hộ");
                txtDonate.requestFocus();
                return false;
            } else {
                long target = Long.parseLong(txtDonate.getText().toString());
                if(target % 1000 != 0) {
                    txtDonate.setError("Số tiền phải chia hết cho 1000");
                    txtDonate.requestFocus();
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            Log.e("DonateActivity", ex.getMessage());
            return false;
        }
    }
}
