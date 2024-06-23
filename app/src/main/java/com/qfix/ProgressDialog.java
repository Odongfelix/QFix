package com.qfix;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Dialog for showing progress
 */
public class ProgressDialog extends BottomSheetDialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.progress);
        setCancelable(false);
        show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dismiss();
            Toast.makeText(context, "Process has taken too long..", Toast.LENGTH_SHORT).show();
        }, 3 * 60 * 1000);
    }
}
