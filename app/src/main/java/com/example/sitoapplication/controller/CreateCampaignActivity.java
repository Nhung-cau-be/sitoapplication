package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.Campaign;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

public class CreateCampaignActivity extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    TextView txtName, txtStory, txtTarget, txtAddress, txtImgName;
    TextInputEditText txtDeadline;
    Button btnCreate, btnChooseImage;
    MaterialDatePicker<Long> datePicker;
    FirebaseStorage storage;
    String imageUrl;
    ImageView imgCampaign;
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

        storage = FirebaseStorage.getInstance("gs://sito-application");
        StorageReference storageRef = storage.getReference();

        txtName = findViewById(R.id.editTextName);
        txtTarget = findViewById(R.id.editTextTarget);
        txtDeadline = findViewById(R.id.editTextDeadline);
        txtAddress = findViewById(R.id.editTextAddress);
        txtStory = findViewById(R.id.editTextStory);
        txtImgName = findViewById(R.id.txtImgName);
        btnChooseImage = findViewById(R.id.buttonChooseImage);
        imgCampaign = findViewById(R.id.imgCampaign);
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
                                txtStory.getText().toString()
                        );
                    }
                } catch (Exception ex) {
                    Log.e("CreateCampaignActivity", ex.getMessage());
                    Toast.makeText(getApplicationContext(), "Tạo chiến dịch thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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
                    txtTarget.setError("Số tiền phải chia hết cho 1.000 VNĐ");
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
        uploadFile();
        Campaign campaign = new Campaign(id, name, target, deadline, address, story, FirebaseAuth.getInstance().getCurrentUser().getUid(), imageUrl);
        ref.set(campaign)
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Tạo chiến dịch thành công", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Log.e("CreateCampaignActivity", "Error creating campaign", e));
        Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
        intent.putExtra("campaign_id", campaign.getId());
        startActivity(intent);
        return campaign;
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgCampaign.setVisibility(View.VISIBLE);
            txtImgName.setVisibility(View.VISIBLE);

            String imageName = getFileName(imageUri);
            txtImgName.setText(imageName);
            imgCampaign.setImageURI(data.getData());
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("campaign/" + System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = storageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrlTask = storageRef.getDownloadUrl();
                    downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            Toast.makeText(CreateCampaignActivity.this, "Upload successful. Image URL: " + imageUrl, Toast.LENGTH_SHORT).show();
                        }
                    });
                    downloadUrlTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateCampaignActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateCampaignActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
