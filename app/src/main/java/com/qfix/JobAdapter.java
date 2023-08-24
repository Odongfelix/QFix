package com.qfix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Job> jobs = new ArrayList<>();

    private String emptyJobText;
    private int emptyImage;

    private boolean isClient;

    public void setClient(boolean client) {
        isClient = client;
    }

    public void setEmptyImage(int emptyImage) {
        this.emptyImage = emptyImage;
    }

    public void setEmptyJobText(String emptyJobText) {
        this.emptyJobText = emptyJobText;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs.clear();
        this.jobs.addAll(jobs);
        notifyDataSetChanged();
    }

    private final int empty = 1;
    private OnMoreClickListener onMoreClickListener;

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View emptyJobView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empty_job_layout, parent, false);
        if (viewType == empty) return new EmptyJobHolder(emptyJobView);

        View jobView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_layout, parent, false);
        return new JobViewHolder(jobView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyJobHolder) {
            EmptyJobHolder emptyJobHolder = (EmptyJobHolder) holder;
            emptyJobHolder.set(emptyJobText);
            emptyJobHolder.set(emptyImage);
        }
        if (holder instanceof JobViewHolder) {
            JobViewHolder jobViewHolder = (JobViewHolder) holder;
            Job job1 = getJob(position);
            if (job1 == null) return;
            jobViewHolder.set(job1);
            View detail = jobViewHolder.itemView.findViewById(R.id.show_job_detail);

            detail.setOnClickListener(v -> {
                if (onMoreClickListener != null) {
                    onMoreClickListener.onDetailsClicked(job1);
                }
            });
            View option = jobViewHolder.itemView.findViewById(R.id.more);
            option.setOnClickListener(v -> {
                if (onMoreClickListener != null) {
                    onMoreClickListener.onMoreClicked(job1);
                }
            });
            if (isClient) {
                detail.setVisibility(View.GONE);
                option.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Job job = getJob(position);
        if (job == null) return empty;
        return 0;
    }

    private Job getJob(int position) {
        try {
            return jobs.get(position);
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return jobs.isEmpty() ? 1 : jobs.size();
    }
}
