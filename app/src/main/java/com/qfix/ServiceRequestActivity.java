package com.qfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;

public class ServiceRequestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        findViewById(R.id.book_now_home).setOnClickListener(b -> bookNow());
        findViewById(R.id.book_now_shop).setOnClickListener(b -> bookNow());
        findViewById(R.id.filter).setOnClickListener(f->new HelpDialog(this));
    }

    private void bookNow() {
        startActivity(new Intent(this, BookNowActivity.class));
    }
}