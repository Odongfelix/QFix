package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisterAsTechnicianActivity extends AccountActivity implements Starter{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_technician);
        findViewById(R.id.submit).setOnClickListener(v -> {
            setRegistered();
            startActivity(this,LoginActivity.class);
        });
    }
}