package com.qfix;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter for technicians
 */
public class TechnicianAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Technician> technicians;
    private final int empty = 1;

    public TechnicianAdapter(ArrayList<Technician> technicians) {
        this.technicians = technicians;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == empty) {
            return new EmptyTechnicianHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.row_empty_technician_near_you, parent, false)
            );
        }
        return new TechnicianHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_technician_near_you, parent, false)
        );
    }

    private Technician getTechnician(int position) {
        try {
            return technicians.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Technician technician = getTechnician(position);
        if (technician == null) return;
        ((TechnicianHolder) holder).set(technician);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(technician);
            }
        });
    }

    @Override
    public int getItemCount() {
        return technicians == null || technicians.isEmpty() ? 1 : technicians.size();
    }

    private OnTechnicianClickListener listener;

    public void setListener(OnTechnicianClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Technician t = getTechnician(position);
        if (t == null) return empty;
        return 0;
    }
}
