package com.example.sitoapplication.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private TextView txtSignIn;
    private TextInputEditText txtEmail, txtPassword, txtRepeatPassword, txtName, txtPhoneNumber, txtDateOfBirth, txtAddress;
    private Button btnSignUp;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        
        txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtRepeatPassword = findViewById(R.id.txtRepeatPassword);
        txtName = findViewById(R.id.txtName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtAddress = findViewById(R.id.txtAddress);
        btnSignUp = findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String repeatPassword = txtRepeatPassword.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String phoneNumber = txtPhoneNumber.getText().toString().trim();
        String dateOfBirth = txtDateOfBirth.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();



        if (email.isEmpty()) {
            txtEmail.setError("Vui lòng nhập email");
            txtEmail.requestFocus();
        }else if (password.isEmpty()) {
            txtPassword.setError("Vui lòng nhập mật khẩu");
            txtPassword.requestFocus();
        } else if (repeatPassword.isEmpty()) {
            txtRepeatPassword.setError("Vui lòng nhập lại mật khẩu");
            txtRepeatPassword.requestFocus();
        } else if(!password.equals(repeatPassword)) {
            Toast.makeText(getApplicationContext(), "Xác nhận mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
            txtRepeatPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        User user = new User(uid, name, phoneNumber, dateOfBirth, address);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").document(uid).set(user);

                        Toast.makeText(getApplicationContext(), "Đăng ký thành công",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}