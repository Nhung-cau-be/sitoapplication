package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.CampaignViewModel;
import com.example.sitoapplication.database.entity.Campaign;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CreateCampaignActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    TextView txtName;
    TextView txtTarget;
    TextInputEditText txtDeadline;
    TextView txtAddress;
    TextView txtStory;
    Button btnCreate;
    MaterialDatePicker<Long> datePicker;

    @SuppressLint("SimpleDateFormat")
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

        txtDeadline.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        datePicker.addOnPositiveButtonClickListener(selection -> txtDeadline.setText(new SimpleDateFormat("dd/MM/yyyy").format(selection)));



        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    if (txtName.getText().toString().isEmpty()) {
                        txtName.setError("Vui lòng nhập tên chiến dịch");
                        txtName.requestFocus();
                    }else if (txtTarget.getText().toString().isEmpty()) {
                        txtTarget.setError("Vui lòng nhập mục tiêu chiến dịch");
                        txtTarget.requestFocus();
                    } else if (txtAddress.getText().toString().isEmpty()) {
                        txtAddress.setError("Vui lòng nhập địa chị");
                        txtAddress.requestFocus();
                    } else if (txtStory.getText().toString().isEmpty()) {
                        txtStory.setError("Vui lòng nhập tiểu sử");
                        txtStory.requestFocus();
                    } else {
                        Campaign campaign = new Campaign();
                        campaign.setName(txtName.getText().toString());
                        campaign.setTarget(Long.parseLong(txtTarget.getText().toString()));
                        campaign.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse(txtDeadline.getText().toString()));
                        campaign.setAddress(txtAddress.getText().toString());
                        campaign.setStory(txtStory.getText().toString());

                        campaignViewModel.insert(campaign);
                        Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    Log.e("CreateCampaignActivity", ex.getMessage());
                }
            }
        });
    }
}
