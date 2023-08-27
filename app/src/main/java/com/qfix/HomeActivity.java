package com.qfix;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.constraintlayout.utils.widget.ImageFilterView;

public class HomeActivity extends AccountActivity {

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