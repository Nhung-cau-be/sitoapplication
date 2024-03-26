package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.Campaign;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCampaignActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtTarget;
    TextInputEditText txtDeadline;
    TextView txtAddress;
    TextView txtStory;
    Button btnCreate;
    MaterialDatePicker<Long> datePicker;
    private MaterialToolbar topAppBar;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);

        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();

        txtName = findViewById(R.id.editTextName);
        txtTarget = findViewById(R.id.editTextTarget);
        txtDeadline = findViewById(R.id.editTextDeadline);
        txtAddress = findViewById(R.id.editTextAddress);
        txtStory = findViewById(R.id.editTextStory);
        btnCreate = findViewById(R.id.buttonCreate);
        topAppBar = findViewById(R.id.create_campaign_top_app_bar);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập",Toast.LENGTH_SHORT).show();
            finish();
        }

        topAppBar.setNavigationOnClickListener(listener -> {
            finish();
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
                    if(validCampaign()) {
                        Campaign campaign = createCampaign(
                                txtName.getText().toString(),
                                Long.parseLong(txtTarget.getText().toString()),
                                new SimpleDateFormat("dd/MM/yyyy").parse(txtDeadline.getText().toString()),
                                txtAddress.getText().toString(),
                                txtStory.getText().toString());

                        Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
                        intent.putExtra("campaign_id", campaign.getId());
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    Log.e("CreateCampaignActivity", ex.getMessage());
                    Toast.makeText(getApplicationContext(), "Tạo chiến dịch thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validCampaign() {
        try {

            if (txtName.getText().toString().isEmpty()) {
                txtName.setError("Vui lòng nhập tên chiến dịch");
                txtName.requestFocus();
                return false;
            }

            if (txtTarget.getText().toString().isEmpty()) {
                txtTarget.setError("Vui lòng nhập mục tiêu chiến dịch");
                txtTarget.requestFocus();
                return false;
            } else {
                long target = Long.parseLong(txtTarget.getText().toString());
                if(target % 1000 != 0) {
                    txtTarget.setError("Số tiền phải chia hết cho 1000");
                    txtTarget.requestFocus();
                    return false;
                } else if (target < 1000000) {
                    txtTarget.setError("Số tiền phải lớn hơn 1.000.000 VNĐ");
                    txtTarget.requestFocus();
                    return false;
                }
            }

            if(txtDeadline.getText().toString().isEmpty()) {
                txtDeadline.setError("Vui lòng chọn ngày hết hạn");
                txtDeadline.requestFocus();
                return false;
            } else {
                Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(txtDeadline.getText().toString());
                if(!deadline.after(new Date())) {
                    txtDeadline.setError("Ngày hết hạn phải sau ngày hôm nay");
                    txtDeadline.requestFocus();
                    return false;
                }
            }

            if (txtAddress.getText().toString().isEmpty()) {
                txtAddress.setError("Vui lòng nhập địa chỉ");
                txtAddress.requestFocus();
                return false;
            }

            if (txtStory.getText().toString().isEmpty()) {
                txtStory.setError("Vui lòng nhập tiểu sử");
                txtStory.requestFocus();
                return false;
            }

            return true;
        } catch (Exception ex) {
            Log.e("CreateCampaignActivity", "Có lỗi xảy ra khi valid dữ liệu trước khi tạo");
            Log.e("CreateCampaignActivity", ex.getMessage());
            return false;
        }
    }

    private Campaign createCampaign(String name, Long target, Date deadline, String address, String story) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("campaign").document();
        String id = ref.getId();

        Campaign campaign = new Campaign(id, name, target, deadline, address, story, FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.set(campaign);

        Toast.makeText(getApplicationContext(), "Tạo chiến dịch thành công",Toast.LENGTH_SHORT).show();

        return campaign;
    }
}
