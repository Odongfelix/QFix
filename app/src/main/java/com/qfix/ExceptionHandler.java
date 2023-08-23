package com.qfix;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

public interface ExceptionHandler {
    default void showTaskException(Task<?> task, Context context) {
        Exception e = task.getException();
        if (e != null)
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
