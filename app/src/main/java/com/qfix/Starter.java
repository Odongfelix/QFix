package com.qfix;

import android.content.Context;
import android.content.Intent;

public interface Starter {
    default void startActivity(Context context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
    }
}
