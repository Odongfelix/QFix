package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TechnicianActivity extends AppCompatActivity {
    private ListView newJobsListView, jobsInProgress, jobsCompleted;
    private Job[] newJobs = {new Job()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);
        newJobsListView = findViewById(R.id.new_jobs);
        jobsInProgress = findViewById(R.id.jobs_in_progress);
        jobsCompleted = findViewById(R.id.jobs_completed);
        newJobsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newJobs));
        jobsInProgress.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"phone screen", "TV speaker"}));
        jobsCompleted.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"phone screen", "TV speaker"}));
        jobsCompleted.setOnItemClickListener((parent, view, position, id) -> {
            showDetail(null);//todo, edit this
        });
        jobsInProgress.setOnItemClickListener((parent, view, position, id) -> {
            showDetail(null);//todo, edit this
        });
        newJobsListView.setOnItemClickListener((parent, view, position, id) -> {
            showDetail(null);//todo, edit this
        });
    }

    private void showDetail(Job job) {
        //todo, show a bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.job_detail);
        dialog.show();
    }
}