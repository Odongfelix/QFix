package com.qfix;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TechnicianActivity extends AppCompatActivity implements Starter, ExceptionHandler {
    protected JobAdapter adapter = new JobAdapter();
    private TextView title;

    protected ArrayList<Job> newJobs, inProgress, completed, selectedTab;
    protected FirebaseUser user;
    protected final MainList<Job> masterList = new MainList<>();

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
        selectedTab = this.newJobs;


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

        user = FirebaseAuth.getInstance().getCurrentUser();
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
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(Constants.JOB_COLLECTION).get().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot d : task.getResult().getDocuments()) {
                    Job job = d.toObject(Job.class);
                    if (job == null) continue;
                    if (job.getTechnician().getUserID().equalsIgnoreCase(user.getUid())) {
                        DocumentReference reference = d.getReference();
                        reference.addSnapshotListener(TechnicianActivity.this,
                                (value, error) -> {
                                    if (error != null || value == null) return;
                                    Job j = value.toObject(Job.class);
                                    Log.d("job found", "job found " + j);
                                    if (j == null) {
                                        Toast.makeText(TechnicianActivity.this, "null", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Log.d("TechnicianActivity.this", "is new " + j + j.isNew());
                                    Log.d("TechnicianActivity.this", "is complete" + j + j.isComplete());
                                    Log.d("TechnicianActivity.this", "is in progress" + j + j.isInProgress());
                                    j.setDocRef(reference);
                                    masterList.add(j);
                                    Log.d("jobs", "jobs " + masterList.size());
                                    organise();
                                });
                    }
                }
            } else showTaskException(task, TechnicianActivity.this);
        });
    }

    protected void addJob(Job job) {
        if (job.isNew()) newJobs.add(job);
        if (job.isComplete()) completed.add(job);
        if (job.isInProgress()) inProgress.add(job);
    }

    protected void changeTabs(View[] tabs) {

    }

    protected void tabClicked(int index) {

    }

    protected void organise() {
        clearTabs();
        for (Job j : masterList) {
            addJob(j);
        }
        adapter.setJobs(selectedTab);
    }

    private void clearTabs() {
        newJobs.clear();
        inProgress.clear();
        completed.clear();
        adapter.setJobs(selectedTab);
    }
}