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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookNowActivity extends AppCompatActivity implements ExceptionHandler, Starter {
    private final ArrayList<Technician> technicians = new ArrayList<>();
    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
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
        firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).whereEqualTo("location", "Banda").addSnapshotListener(this, (value, error) -> {
            if (error != null || value == null) {
                if (error != null) {
                    Throwable cause = error.getCause();
                    if (cause == null) return;
                    Toast.makeText(BookNowActivity.this, cause.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            for (DocumentSnapshot d : value.getDocuments()) {
                Object o = d.get("name");
                if (o == null) continue;
                String name = o.toString();
                o = d.get("repairService");
                if (o == null) continue;
                String service = o.toString();
                Technician t = new Technician(name, service);
                o = d.get("userID");
                if (o == null) continue;
                t.setUserID(o.toString());
                technicians.add(0, t);
                technicians.add(0, t);
                technicians.add(0, t);
                adapter.notifyDataSetChanged();
            }
        });
    }

}