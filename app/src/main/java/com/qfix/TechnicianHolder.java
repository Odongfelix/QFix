package com.qfix;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TechnicianHolder extends RecyclerView.ViewHolder {
    private final TextView name;
    private final TextView place;
    public TechnicianHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.phone_);
        place = itemView.findViewById(R.id.place);
    }

    public void set(Technician technician) {
        name.setText(technician.getName());
        place.setText(technician.getRepairService());
    }
}
