package com.qfix;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AccountActivity implements Starter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.login).setOnClickListener(l -> {
            startActivity(new Intent(RegistrationActivity.this, RegisterAsTechnicianActivity.class));
        });
        findViewById(R.id.login_client).setOnClickListener(c -> {
            setRegistered();
            startActivity(this, LoginActivity.class);
        });
    }
}