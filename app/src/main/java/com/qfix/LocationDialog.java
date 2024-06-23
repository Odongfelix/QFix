package com.qfix;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Dialog for locations
 */
public class LocationDialog extends BottomSheetDialog {
    private OnLocationSelectedListener listener;

    public LocationDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.location);
        show();
        View decor = getWindow().getDecorView();
        EditText location = decor.findViewById(R.id.location);
        decor.findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onLocationSelected(location.getText().toString());
            }
        });
    }

    public void setListener(OnLocationSelectedListener listener) {
        this.listener = listener;
    }
}
