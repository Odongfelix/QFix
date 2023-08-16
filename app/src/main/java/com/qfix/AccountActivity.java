package com.qfix;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends DataBaseActivity {
    protected boolean isRegistered() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRIMITIVES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.REGISTERED,false);
    }

    protected void setRegistered(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRIMITIVES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.REGISTERED,true);
        editor.apply();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //haven't the use for overriding this but that's nt a problem
    }
}
