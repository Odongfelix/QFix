package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ClientDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash_board);
        findViewById(R.id.cart).setOnClickListener(v -> {
            startActivity(new Intent(ClientDashBoard.this, Cart.class));
        });
    }
}