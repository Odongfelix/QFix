package com.qfix;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Dialog fo choosing the account type
 * A listener is used to handle selections
 * on the dialog
 */

public class AccountTypeDialog extends BottomSheetDialog {
    private OnAccountTypeSelectedListener listener;
    public AccountTypeDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.account_type);
        View decor = getWindow().getDecorView();
        decor.findViewById(R.id.technician).setOnClickListener(v -> {
            dismiss();
            listener.onTechnicianAccountSelected();
        });
        decor.findViewById(R.id.client).setOnClickListener(v -> {
            dismiss();
            listener.onClientAccountSelected();
        });
        show();
    }

    public void setListener(OnAccountTypeSelectedListener listener) {
        this.listener = listener;
    }
}
