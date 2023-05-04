package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login).setOnClickListener(l -> {
            startActivity(new Intent(LoginActivity.this, ClientDashBoard.class));
        });
        findViewById(R.id.create_account).setOnClickListener(l -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }
}