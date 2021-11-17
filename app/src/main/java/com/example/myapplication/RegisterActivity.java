package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText rEmail,rPassword,rConfirmPassword;
    Button registerUser;
    TextView goToLogin;
    FirebaseAuth rAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        rConfirmPassword = findViewById(R.id.rConfirmPassword);

        registerUser = findViewById(R.id.registerUser);
        goToLogin = findViewById(R.id.goToLogin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        rAuth = FirebaseAuth.getInstance();
        if(rAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Firstpagelogin.class));
            finish();
        }

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Firstpagelogin.class));
            }
        });

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                String confirm = rConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    rEmail.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(confirm)){
                    rEmail.setError("Confirm Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    rPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    rPassword.setError("Password must be greater than 5 characters");
                    return;
                }
                if(!password.equals(confirm)){
                    rConfirmPassword.setError("Confirm Password must be same with Password");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                rAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Firstpagelogin.class));
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Error !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}