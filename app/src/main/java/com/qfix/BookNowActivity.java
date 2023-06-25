package com.qfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class BookNowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TechnicianAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Technician> technicians = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.go_to_cart).setOnClickListener(v->startActivity(new Intent(this,DashboardActivity.class)));
        adapter = new TechnicianAdapter(technicians);
        linearLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}