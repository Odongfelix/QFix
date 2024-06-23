package com.qfix;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

/**
 * An activity for checking if the current user is registered, is a client
 * and saving account type
 */
public class AccountActivity extends DataBaseActivity {
    protected boolean isRegistered() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    protected boolean isClientAccount() {
        SharedPreferences prefs = getSharedPreferences(Constants.PRIMITIVES, MODE_PRIVATE);
        return prefs.getBoolean(Constants.ACCOUNT_TYPE_CLIENT, true);
    }

    protected void saveAccountType(boolean client) {
        SharedPreferences prefs = getSharedPreferences(Constants.PRIMITIVES, MODE_PRIVATE);
        prefs.edit().putBoolean(Constants.ACCOUNT_TYPE_CLIENT, client).apply();
    }

}
