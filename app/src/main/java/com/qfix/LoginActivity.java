package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AccountActivity implements Starter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //todo, check if the client is registered before getting all crazy
        findViewById(R.id.login_client).setOnClickListener(l -> {
            startActivity(this, ServiceRequestActivity.class);
        });
        findViewById(R.id.login).setOnClickListener(l -> {
            startActivity(this, TechnicianActivity.class);
        });
        findViewById(R.id.create_account).setOnClickListener(l -> {
            startActivity(this, RegistrationActivity.class);
        });
    }
}