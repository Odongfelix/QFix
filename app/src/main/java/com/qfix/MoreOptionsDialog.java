package com.qfix;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MoreOptionsDialog extends BottomSheetDialog {
    public MoreOptionsDialog(@NonNull Context context,Job job) {
        super(context);
        setContentView(R.layout.more_options);
        show();
        getWindow().getDecorView().findViewById(R.id.mark_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                job.setNew(false);
                job.getDocRef().set(job);
            }
        });
    }
}
