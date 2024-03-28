package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserActivity extends AppCompatActivity {
    private TextView userNameTextView;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar txtCreateCampaign;
    private MaterialToolbar txtUserProfile;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        userNameTextView = findViewById(R.id.user_layout_name);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txtCreateCampaign = findViewById(R.id.txtCreateCampaign);
        txtUserProfile = findViewById(R.id.txtUserProfile);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("user").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User currentUser = document.toObject(User.class);
                            userNameTextView.setText(currentUser.getName());
                        } else {
                            Log.e("TAG", "No such document");
                        }
                    } else {
                        Log.e("TAG", "get failed with ", task.getException());
                    }
                }
            });

        } else {
            Log.e("TAG", "User is null");
        }

        bottomNavigationView.setSelectedItemId(R.id.bnm_user);
        txtCreateCampaign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateCampaignActivity.class);
                startActivity(intent);
            }
        });
      
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bnm_home) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });

        txtUserProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
