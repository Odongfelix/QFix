package com.qfix;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianHolder> {
    private ArrayList<Technician> technicians;

    public TechnicianAdapter(ArrayList<Technician> technicians) {
        this.technicians = technicians;
    }

    @NonNull
    @Override
    public TechnicianHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TechnicianHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_technician_near_you,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicianHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;//technicians.size();
    }
}
