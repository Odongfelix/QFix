package com.qfix;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MoreOptionsDialog extends BottomSheetDialog {
    public MoreOptionsDialog(@NonNull Context context,Job job) {
        super(context);
        setContentView(R.layout.more_options);
        show();
    }
}
