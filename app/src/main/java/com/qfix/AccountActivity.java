package com.qfix;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends DataBaseActivity {
    protected boolean isRegistered() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //haven't seen the use for overriding this but that's nt a problem
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
