package com.qfix;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DataBaseActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHelper = new DataBaseHelper(this);
    }

    protected boolean isRegistered(String emailOrPhone, String password) {
        //todo check if this combination of email and password exists in the data base
        // or if this combination of phone and password exists in the data base
        // or use pattern matching to clearly identify whether this is phone or email n continue accordingly
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String selection = "email LIKE?" + " password LIKE?"; // change the selection string as password is constant
        String[] selectionArgs = {emailOrPhone, password};
        Cursor cursor = db.query("accounts", DataBaseUtility.ACCOUNT_COLUMNS, selection, selectionArgs, null, null, null);
        boolean registered = cursor.getCount() > 0;
        cursor.close();
        return registered;
    }
}
