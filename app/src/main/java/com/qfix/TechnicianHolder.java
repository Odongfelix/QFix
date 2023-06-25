package com.qfix;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TechnicianHolder extends RecyclerView.ViewHolder {
    private CircularImageView dp;
    private TextView name,place;
    public TechnicianHolder(@NonNull View itemView) {
        super(itemView);
    }
}
