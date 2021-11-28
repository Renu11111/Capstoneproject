package com.app.myapplication;


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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Firstpagelogin extends AppCompatActivity {

    TextView goToRegister;
    EditText lEmail, lPassword;
    Button userLogin;
    ProgressBar progressBar;
    FirebaseAuth rAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpagelogin);

        goToRegister = findViewById(R.id.goToRegister);
        lEmail = findViewById(R.id.lEmail);
        lPassword = findViewById(R.id.lPassword);
        userLogin = findViewById(R.id.userLogin);
        progressBar = findViewById(R.id.lProgressBar);

        rAuth = FirebaseAuth.getInstance();

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    lPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    lPassword.setError("Password must be greater than 5 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                rAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Firstpagelogin.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Firstpagelogin.this,home.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Firstpagelogin.this,"Error !"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(Firstpagelogin.this,home.class);
            startActivity(intent);
        }
    }
}
