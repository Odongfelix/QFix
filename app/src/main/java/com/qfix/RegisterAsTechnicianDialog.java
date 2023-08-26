package com.qfix;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RegisterAsTechnicianDialog extends BottomSheetDialog {
    private OnRegisterListener onRegisterListener;

    public RegisterAsTechnicianDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.activity_register_as_technician);
        View decor = getWindow().getDecorView();
        EditText name = decor.findViewById(R.id.email),
        businessName = decor.findViewById(R.id.phone),
        repairService = decor.findViewById(R.id.location);
        decor.findViewById(R.id.submit).setOnClickListener(v -> {
            dismiss();
            if(onRegisterListener != null)
                onRegisterListener.onRegister(name.getText().toString(),
                        businessName.getText().toString(),
                        repairService.getText().toString());
        });
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        show();
    }

    public void setOnRegisterListener(OnRegisterListener onRegisterListener) {
        this.onRegisterListener = onRegisterListener;
    }
}
