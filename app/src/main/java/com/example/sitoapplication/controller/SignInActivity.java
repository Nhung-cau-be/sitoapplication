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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText txtEmail, txtPassword;
    private Button btnSignIn;
    private TextView txtCreateAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);

        mAuth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
//                Account account = new Account(txtEmail.getText().toString().trim(),txtPassword.getText().toString().trim());
//
//                if(account.loginSuccess()) {
//                    Intent intent = new Intent(SignInActivity.this, ListCampaignActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            txtEmail.setError("Vui lòng nhập email");
            txtEmail.requestFocus();
        }
        if (password.isEmpty()) {
            txtPassword.setError("Vui lòng nhập mật khẩu");
            txtPassword.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this,ListCampaignActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}