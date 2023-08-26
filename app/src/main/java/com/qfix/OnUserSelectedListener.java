package com.qfix;

import com.google.firebase.auth.AuthCredential;

public interface OnUserSelectedListener {
    void onUserSelected(AuthCredential authCredential);
}
