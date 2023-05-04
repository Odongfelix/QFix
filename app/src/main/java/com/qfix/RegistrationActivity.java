package com.qfix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.login).setOnClickListener(l -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
        RelativeLayout name = findViewById(R.id.email_cover),
        businessName = findViewById(R.id.phone_cover),
        service = findViewById(R.id.location_cover),
        password = findViewById(R.id.password_cover);
        TextView clientLogin = findViewById(R.id.login_client);
        ImageView back = findViewById(R.id.back_);
        TextView login = findViewById(R.id.login);
        login.setOnClickListener(l->{
            if(login.getText().toString().equalsIgnoreCase("register as technician")){
                login.setText("submit");
                password.setVisibility(View.GONE);
                clientLogin.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                ((ImageView) name.getChildAt(1)).setImageResource(R.drawable.contact);
                ((EditText) name.getChildAt(0)).setHint("Name");

                ((ImageView) businessName.getChildAt(1)).setImageResource(R.drawable.business);
                ((EditText) businessName.getChildAt(0)).setHint("Business Name");

                ((ImageView) service.getChildAt(1)).setImageResource(R.drawable.build);
                ((EditText) service.getChildAt(0)).setHint("Repair service");
                return;
            }
            //todo, proceed with signing in here
        });
        back.setOnClickListener(b->{
            login.setText("register as technician");
            password.setVisibility(View.VISIBLE);
            clientLogin.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            ((ImageView) name.getChildAt(1)).setImageResource(R.drawable.email);
            ((EditText) name.getChildAt(0)).setHint("Email");

            ((ImageView) businessName.getChildAt(1)).setImageResource(R.drawable.phone);
            ((EditText) businessName.getChildAt(0)).setHint("Phone");

            ((ImageView) service.getChildAt(1)).setImageResource(R.drawable.location);
            ((EditText) service.getChildAt(0)).setHint("location");
        });
    }
}