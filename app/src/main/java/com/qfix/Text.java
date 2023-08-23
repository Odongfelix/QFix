package com.qfix;

import android.text.TextUtils;

public interface Text {
    default boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }
}
