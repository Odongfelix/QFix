package com.qfix;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AccountActivity implements Starter, Text, ExceptionHandler {
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.useEmulator("10.0.2.2", 9099);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.useEmulator("10.0.2.2", 8080);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (isClientAccount())
                startActivity(this, DashboardActivity.class);
            if (!isClientAccount())
                startActivity(LoginActivity.this, TechnicianActivity.class);
            finish();
        }

        //todo, check if the client is registered before getting all crazy
        findViewById(R.id.login_client).setOnClickListener(l -> {
            if (isEmpty(email.getText()) || isEmpty(password.getText())) {
                Toast.makeText(this, "Password and email are required for this operation", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(LoginActivity.this, DashboardActivity.class);
                            finish();
                        } else {
                            showTaskException(task,this);
                        }
                    });
        });
        findViewById(R.id.login).setOnClickListener(l -> {
            if (isEmpty(email.getText()) || isEmpty(password.getText())) {
                Toast.makeText(this, "Password and email are required for this operation", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            saveAccountType(false);
                            startActivity(LoginActivity.this, TechnicianActivity.class);
                            finish();
                        } else {
                            showTaskException(task,this);
                        }
                    });
        });
        findViewById(R.id.create_account).setOnClickListener(l -> {
            startActivity(this, RegistrationActivity.class);
            finish();
        });
    }
}