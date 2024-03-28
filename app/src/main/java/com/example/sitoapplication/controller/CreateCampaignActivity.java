package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.Address;
import com.example.sitoapplication.model.Campaign;
import com.example.sitoapplication.model.District;
import com.example.sitoapplication.model.Province;
import com.example.sitoapplication.model.Ward;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class CreateCampaignActivity extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    TextView txtName, txtStory, txtTarget, txtStreetName, txtImgName;
    TextInputEditText txtDeadline;
    Button btnCreate, btnChooseImage;
    MaterialDatePicker<Long> datePicker;
    FirebaseStorage storage;
    String imageUrl;
    ImageView imgCampaign;
    private MaterialToolbar topAppBar;
    private FirebaseFirestore db;
    private MaterialAutoCompleteTextView txtProvince;
    private MaterialAutoCompleteTextView txtDistrict;
    private MaterialAutoCompleteTextView txtWard;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_campaign);

        db = FirebaseFirestore.getInstance();
        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();

        storage = FirebaseStorage.getInstance("gs://sito-application");
        StorageReference storageRef = storage.getReference();

        txtName = findViewById(R.id.editTextName);
        txtTarget = findViewById(R.id.editTextTarget);
        txtDeadline = findViewById(R.id.editTextDeadline);
        txtStreetName = findViewById(R.id.create_campaign_txt_street);
        txtStory = findViewById(R.id.editTextStory);
        txtImgName = findViewById(R.id.txtImgName);
        btnChooseImage = findViewById(R.id.buttonChooseImage);
        imgCampaign = findViewById(R.id.imgCampaign);
        btnCreate = findViewById(R.id.buttonCreate);
        topAppBar = findViewById(R.id.create_campaign_top_app_bar);
        txtProvince = findViewById(R.id.create_campaign_spinner_province);
        txtDistrict = findViewById(R.id.create_campaign_spinner_district);
        txtWard = findViewById(R.id.create_campaign_spinner_ward);

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
                        uploadFileAndCreateCampaign();
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

        bindingDataProvince();
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

            if (txtStreetName.getText().toString().isEmpty()) {
                txtStreetName.setError("Vui lòng nhập số nhà, tên đường");
                txtStreetName.requestFocus();
                return false;
            }
            if (txtProvince.getText().toString().isEmpty()) {
                txtStreetName.setError("Vui lòng chọn tỉnh/ thành phố");
                txtStreetName.requestFocus();
                return false;
            }
            if (txtDistrict.getText().toString().isEmpty()) {
                txtDistrict.setError("Vui lòng chọn địa chỉ");
                txtDistrict.requestFocus();
                return false;
            }
            if (txtWard.getText().toString().isEmpty()) {
                txtWard.setError("Vui lòng chọn phường/ xã  ");
                txtWard.requestFocus();
                return false;
            }
            return true;
        } catch (Exception ex) {
            Log.e("CreateCampaignActivity", "Có lỗi xảy ra khi valid dữ liệu trước khi tạo");
            Log.e("CreateCampaignActivity", ex.getMessage());
            return false;
        }
    }

    private Campaign createCampaign() {
        try {
            DocumentReference ref = db.collection("campaign").document();
            String id = ref.getId();
            String name = txtName.getText().toString();
            Long target = Long.parseLong(txtTarget.getText().toString());
            Date deadline = new SimpleDateFormat("dd/MM/yyyy").parse(txtDeadline.getText().toString());
            String streetName = txtStreetName.getText().toString();
            String wardName = txtWard.getText().toString();
            String districtName = txtDistrict.getText().toString();
            String provinceName = txtProvince.getText().toString();
            String story = txtStory.getText().toString();
            Campaign campaign = new Campaign(id, name, target, deadline, new Address(streetName, wardName, districtName, provinceName), story, FirebaseAuth.getInstance().getCurrentUser().getUid(), imageUrl);
            ref.set(campaign)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Tạo chiến dịch thành công", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Log.e("CreateCampaignActivity", "Error creating campaign", e));
            Intent intent = new Intent(CreateCampaignActivity.this, ListCampaignActivity.class);
            intent.putExtra("campaign_id", campaign.getId());
            startActivity(intent);
            return campaign;
        } catch (Exception ex) {
            Log.e("CreateCampaignActivity", "Tạo chiến dịch thất bại");
            return null;
        }
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

    private void uploadFileAndCreateCampaign() {
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
                            Campaign campaign = createCampaign();
                            Log.e("CreateCampaignActivity", "Upload successful. Image URL: " + imageUrl);

                        }
                    });
                    downloadUrlTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("CreateCampaignActivity", "Failed to get download URL");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateCampaignActivity.this, "Tải hình lên thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("CreateCampaignActivity", "Upload failed: " + e.getMessage());
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

    private void bindingDataProvince() {
        db = FirebaseFirestore.getInstance();
        db.collection("province")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Province> provinces = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                provinces.add(document.toObject(Province.class));
                            }
                            txtProvince.setSimpleItems(provinces.stream().map(Province::getName).toArray(String[]::new));
                            txtProvince.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    String provinceName = s.toString();
                                    txtDistrict.setSimpleItems(new String[0]);
                                    txtDistrict.setText("");
                                    txtWard.setSimpleItems(new String[0]);
                                    txtWard.setText("");
                                    bindingDataDistrict(provinces, provinceName);
                                }
                            });
                        } else {
                            Log.d("HomeSearchUserFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void bindingDataDistrict(List<Province> provinces, String provinceName) {
        Province province = provinces.stream().filter(p -> p.getName().equals(provinceName)).findFirst().orElse(null);
        if (province != null) {
            List<District> districts = province.getDistricts();
            txtDistrict.setSimpleItems(districts.stream().map(District::getName).toArray(String[]::new));
            txtDistrict.setEnabled(true);
            txtWard.setEnabled(false);

            txtDistrict.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    txtWard.setSimpleItems(new String[0]);
                    txtWard.setText("");
                    bindingDataWard(provinces, provinceName, s.toString());
                }
            });
        }
    }

    private void bindingDataWard(List<Province> provinces, String provinceName, String districtName) {
        Province province = provinces.stream().filter(p -> p.getName().equals(provinceName)).findFirst().orElse(null);
        if (province != null) {
            District district = province.getDistricts().stream().filter(d -> d.getName().equals(districtName)).findFirst().orElse(null);
            if(district != null) {
                List<Ward> wards = district.getWards();
                txtWard.setSimpleItems(wards.stream().map(Ward::getName).toArray(String[]::new));
                txtWard.setEnabled(true);
            }
        }
    }
}
