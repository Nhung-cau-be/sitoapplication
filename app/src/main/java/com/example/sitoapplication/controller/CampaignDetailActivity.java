package com.example.sitoapplication.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.CampaignViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CampaignDetailActivity extends AppCompatActivity {
    private CampaignViewModel campaignViewModel;
    TextView txtName;
    TextView txtTarget;
    TextView txtDeadline;
    TextView txtAddress;
    TextView txtStory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_detail);
        String campaignId = String.valueOf(getIntent().getLongExtra("campaign_id", -1));

        campaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);

        txtName = findViewById(R.id.txtName);
        txtTarget = findViewById(R.id.txtTarget);
        txtDeadline = findViewById(R.id.txtDeadline);
        txtAddress = findViewById(R.id.txtAddress);
        txtStory = findViewById(R.id.txtStory);


        campaignViewModel.getById(campaignId).observe(this, campaign -> {
            if (campaign != null) {
                txtName.setText(campaign.getName());
               txtTarget.setText(AsCurrency((campaign.getTarget())));
               // txtDeadline.setText(Math.toIntExact(campaign.getDeadline()));
                txtAddress.setText(campaign.getAddress());
                txtStory.setText(campaign.getStory());
            } else {
                Log.d("CampaignDetailActivity", "Không tìm thấy chiến dịch");
            }
        });
    }

    public String AsCurrency( long value)
    {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(value) + " VNĐ";
    }
}
