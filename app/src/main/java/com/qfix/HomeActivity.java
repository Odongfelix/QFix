package com.qfix;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AccountActivity {
    //todo, this is a test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isRegistered()) {
            startLoginActivity();
            finish();
        }
        setContentView(R.layout.activity_home);
        findViewById(R.id.get_started).setOnClickListener(g -> {
            startLoginActivity();
            finish();
        });
    }

    private void startLoginActivity() {
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
}