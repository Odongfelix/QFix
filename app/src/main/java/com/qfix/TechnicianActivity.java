package com.qfix;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TechnicianActivity extends AppCompatActivity implements Starter, ExceptionHandler {
    protected JobAdapter adapter = new JobAdapter();
    private TextView title;

    protected ArrayList<Job> newJobs, inProgress, completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);
        RecyclerView jobsRecyclerView = findViewById(R.id.recycler_view);
        title = findViewById(R.id.title);
        View newJobs = findViewById(R.id.new_layout);
        View jobsInProgress = findViewById(R.id.in_progress_layout);
        View completedJobs = findViewById(R.id.completed_layout);
        findViewById(R.id.help).setOnClickListener(v -> new HelpDialog(this));

        this.newJobs = new ArrayList<>();
        this.inProgress = new ArrayList<>();
        this.completed = new ArrayList<>();

        Job e1 = new Job();
        Client c = new Client();
        c.setName("Odong felix");
        c.setPhone("0123456789");
        c.setLocation("Mbale");
        e1.setClient(c);
        Electronic electronic = new Electronic();
        electronic.setName("Samsang");
        electronic.setModel("S22 Ultra");
        electronic.setManufacturer("Broken Screen");
        electronic.setDetails("Screen got broken, it can still display but the sensor doesn't work");
        e1.setElectronic(electronic);
        this.newJobs.add(e1);


        this.inProgress.add(e1);

        this.completed.add(e1);

        adapter.setJobs(this.newJobs);

        View[] tabs = {newJobs, jobsInProgress, completedJobs};
        newJobs.setOnClickListener(v -> {
            select(v, tabs);
            adapter.setJobs(this.newJobs);
            tabClicked(0);
        });
        jobsInProgress.setOnClickListener(v -> {
            select(v, tabs);
            adapter.setJobs(this.inProgress);
            tabClicked(1);
        });
        completedJobs.setOnClickListener(v -> {
            select(v, tabs);
            adapter.setJobs(this.completed);
            tabClicked(2);
        });
        changeTabs(tabs);

        adapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClicked(Job job) {
                new MoreOptionsDialog(TechnicianActivity.this, job);
            }

            @Override
            public void onDetailsClicked(Job job) {
                new JobDetailsDialog(TechnicianActivity.this, job);
            }
        });
        adapter.setEmptyImage(R.drawable.outline_remove_shopping_cart_24);
        adapter.setEmptyJobText("You have no running repair requests. Your requests will appear here");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        jobsRecyclerView.setLayoutManager(linearLayoutManager);
        jobsRecyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        if (user != null) {
            firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION)
                    .get().addOnCompleteListener(TechnicianActivity.this,
                            task -> {
                                if (task.isSuccessful()) {
                                    if (user.getEmail() == null) return;
                                    firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION)
                                            .document(user.getUid())
                                            .get()
                                            .addOnCompleteListener(this, task1 -> {
                                                try {
                                                    Object name = task1.getResult().get("name");
                                                    if (name == null) return;
                                                    setTitle(name.toString());
                                                } catch (Exception e) {
                                                    Throwable cause = e.getCause();
                                                    if (cause == null) return;
                                                    Toast.makeText(this, cause.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else showTaskException(task, this);
                            });
            loadRecyclerViewData();
        }

    }

    private void select(View selected, View[] from) {
        for (View v : from) {
            v.setAlpha(v == selected ? 1f : .3f);
        }
    }

    protected void setTitle(String title) {
        this.title.setText(title);
    }

    protected void loadRecyclerViewData() {

    }

    protected void changeTabs(View[] tabs) {

    }

    protected void tabClicked(int index) {

    }
}