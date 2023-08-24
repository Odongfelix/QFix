package com.qfix;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class JobDetailsDialog extends BottomSheetDialog {
    public JobDetailsDialog(@NonNull Context context,Job job) {
        super(context);
        if(job == null) return;
        setContentView(R.layout.job_detail);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        View decor = getWindow().getDecorView();
        Electronic electronic = job.getElectronic();
        TextView deviceName = decor.findViewById(R.id.device_name);
        deviceName.setText(electronic.getName());
        TextView model = decor.findViewById(R.id.model);
        model.setText(electronic.getModel());
        TextView problem = decor.findViewById(R.id.problem);
        problem.setText(electronic.getManufacturer());
        TextView description = decor.findViewById(R.id.password);
        description.setText(electronic.getDetails());
        Client client = job.getClient();
        TextView name = decor.findViewById(R.id.phone_);
        name.setText(client.getName());
        TextView phone = decor.findViewById(R.id.phone_no);
        phone.setText(client.getPhone());
        TextView location = decor.findViewById(R.id.location);
        location.setText(client.getLocation());
        show();
    }
}
