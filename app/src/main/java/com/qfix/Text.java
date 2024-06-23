package com.qfix;

import android.text.TextUtils;

/**
 * For checking is a text is empty
 */
public interface Text {
    default boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }
}
