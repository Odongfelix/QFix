package com.qfix;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        });
        findViewById(R.id.create_account).setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        });
    }
}