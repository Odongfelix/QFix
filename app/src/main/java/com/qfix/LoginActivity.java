package com.qfix;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(email.getText())) {
                    Toast.makeText(LoginActivity.this, "You must provide your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(LoginActivity.this, "email sent to " + email.getText(), Toast.LENGTH_SHORT).show();
                        else {
                            Exception exception = task.getException();
                            if (exception == null) return;
                            Throwable t = exception.getCause();
                            if (t == null) {
                                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(LoginActivity.this, "could not send an email to " + email.getText() + " because " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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
            ProgressDialog p = new ProgressDialog(LoginActivity.this);
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        p.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(LoginActivity.this, DashboardActivity.class);
                            finish();
                        } else {
                            showTaskException(task, this);
                        }
                    });
        });
        findViewById(R.id.login).setOnClickListener(l -> {
            if (isEmpty(email.getText()) || isEmpty(password.getText())) {
                Toast.makeText(this, "Password and email are required for this operation", Toast.LENGTH_SHORT).show();
                return;
            }
            ProgressDialog p = new ProgressDialog(LoginActivity.this);
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        p.dismiss();
                        if (task.isSuccessful()) {
                            saveAccountType(false);
                            startActivity(LoginActivity.this, TechnicianActivity.class);
                            finish();
                        } else {
                            showTaskException(task, this);
                        }
                    });
        });
        findViewById(R.id.create_account).setOnClickListener(l -> {
            startActivity(this, RegistrationActivity.class);
            finish();
        });
    }
    //todo, remember to update SHA 1 to the release version
}