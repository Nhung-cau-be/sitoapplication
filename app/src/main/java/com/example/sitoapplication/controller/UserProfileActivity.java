package com.example.sitoapplication.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {
    private TextView txtMainName,txtName;
    @SuppressLint("SimpleDateFormat")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        txtMainName = findViewById(R.id.txtUserProfileName);
        txtName = findViewById(R.id.txtUserProfileName1);

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
                            txtMainName.setText(currentUser.getName());
                            txtName.setText(currentUser.getName());
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
    }
}
