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
import com.example.sitoapplication.entity.Campaign;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;

public class CreateCampaignActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    TextView txtName;
    TextView txtTarget;
    TextView txtDeadline;
    TextView txtAddress;
    TextView txtStory;
    Button btnCreate;
    MaterialDatePicker<Long> datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);

        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();

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

        txtDeadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                }
            }
        });
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                txtDeadline.setText(new SimpleDateFormat("dd/MM/yyyy").format(selection));
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Campaign campaign = new Campaign();
                campaign.setName(txtName.getText().toString());
                campaign.setTarget(Long.parseLong(txtTarget.getText().toString()));

                campaignViewModel.insert(campaign);

                Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
                startActivity(intent);
            }
        });
    }
}
