package com.qfix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class BookNowActivity extends AppCompatActivity implements ExceptionHandler, Starter, OnMapReadyCallback {
    private final ArrayList<Technician> technicians = new ArrayList<>();
    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        findViewById(R.id.back).setOnClickListener(v -> finish());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.go_to_cart).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));


        job = (Job) getIntent().getSerializableExtra("job");

        TechnicianAdapter adapter = new TechnicianAdapter(technicians);
        adapter.setListener(technician -> {
            if (job == null) return;
            ProgressDialog progressDialog = new ProgressDialog(BookNowActivity.this);
            job.setTechnician(technician);
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection(Constants.JOB_COLLECTION).document().set(job)
                    .addOnCompleteListener(BookNowActivity.this, task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(BookNowActivity.this, DashboardActivity.class);
                            finish();
                        } else showTaskException(task, BookNowActivity.this);
                    });
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).whereEqualTo("location", "kirinya").addSnapshotListener(this, (value, error) -> {
            if (error != null || value == null) {
                if (error != null) {
                    Throwable cause = error.getCause();
                    if (cause == null) return;
                    Toast.makeText(BookNowActivity.this, cause.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            technicians.clear();
            for (DocumentSnapshot d : value.getDocuments()) {
                Technician o = d.toObject(Technician.class);
                technicians.add(o);
            }
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}