package com.qfix;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class HelpDialog extends BottomSheetDialog {
    public HelpDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.help_dialog);
        show();
    }
}
