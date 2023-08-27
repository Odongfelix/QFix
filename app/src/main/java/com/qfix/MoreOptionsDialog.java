package com.qfix;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;

public class MoreOptionsDialog extends BottomSheetDialog {
    public MoreOptionsDialog(@NonNull Context context, Job job) {
        super(context);
        setContentView(R.layout.more_options);
        show();
        getWindow().getDecorView().findViewById(R.id.mark_complete).setOnClickListener(v -> {
            dismiss();
            job.setNew(false);
            job.setInProgress(false);
            job.setRejected(false);
            job.setComplete(true);
            DocumentReference docRef = job.getDocRef();
            job.setDocRef(null);
            docRef.set(job);
        });

        getWindow().getDecorView().findViewById(R.id.mark_in_progress).setOnClickListener(v -> {
            dismiss();
            job.setNew(false);
            job.setInProgress(true);
            job.setComplete(false);
            job.setRejected(false);
            DocumentReference docRef = job.getDocRef();
            job.setDocRef(null);
            docRef.set(job);
        });

        getWindow().getDecorView().findViewById(R.id.reject).setOnClickListener(v -> {
            dismiss();
            job.setNew(false);
            job.setInProgress(false);
            job.setComplete(false);
            job.setRejected(true);
            DocumentReference docRef = job.getDocRef();
            job.setDocRef(null);
            docRef.set(job);
        });
    }
}
