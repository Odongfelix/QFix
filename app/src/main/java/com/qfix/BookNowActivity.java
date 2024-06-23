package com.qfix;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookNowActivity extends AppCompatActivity implements ExceptionHandler, Starter, OnMapReadyCallback {
    private final ArrayList<Technician> technicians = new ArrayList<>();
    private Job job;
    private TechnicianAdapter adapter;

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
        findViewById(R.id.go_to_cart).setOnClickListener(v -> {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        });

        job = (Job) getIntent().getSerializableExtra("job");
        job.setInProgress(true);

        adapter = new TechnicianAdapter(technicians);
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
        firebaseFirestore.collection(Constants.CLIENT_COLLECTION).document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .get().addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            Client client = task.getResult().toObject(Client.class);
                            if(client==null) {
                                Toast.makeText(BookNowActivity.this, "Error retrieving your location", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).whereEqualTo("location", client.getLocation().toLowerCase()).addSnapshotListener(BookNowActivity.this, (value, error) -> {
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

                        }else showTaskException(task,BookNowActivity.this);
                    }
                });
         }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        /*googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(3));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                Geocoder geocoder = new Geocoder(BookNowActivity.this);
                try {
                    String countryName = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getLocality();
                    FirebaseFirestore.getInstance().collection(Constants.TECHNICIAN_COLLECTION).whereEqualTo("location", countryName).addSnapshotListener(BookNowActivity.this, (value, error) -> {
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
                    Toast.makeText(BookNowActivity.this, ""+countryName, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(BookNowActivity.this, "Failed to process location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}