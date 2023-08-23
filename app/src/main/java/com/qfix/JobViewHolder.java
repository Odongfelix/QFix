package com.qfix;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobViewHolder extends RecyclerView.ViewHolder {
    private final TextView job;
    private final TextView jobDetail;

    public JobViewHolder(@NonNull View itemView) {
        super(itemView);
        job = itemView.findViewById(R.id.job);
        jobDetail = itemView.findViewById(R.id.job_detail);
    }

    public void set(Job job) {
        if (job == null) {
            return;
        }
        Electronic electronic = job.getElectronic();
        if (electronic == null) {
            return;
        }
        this.job.setText(electronic.getProblem());
        jobDetail.setText(electronic.getProblemDescription());
    }
}
