package com.qfix;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class RegistrationActivity extends AccountActivity implements Starter, ExceptionHandler {
    private EditText email, password;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        try {
            firebaseAuth.useEmulator("10.0.2.2", 9099);
            firebaseFirestore.useEmulator("10.0.2.2", 8080);
        } catch (Exception ignored) {

        }


        findViewById(R.id.login).setOnClickListener(l -> {
            new RegisterAsTechnicianDialog(RegistrationActivity.this).setOnRegisterListener((name, businessName, repairService) -> {
                Toast.makeText(RegistrationActivity.this, name + businessName + repairService, Toast.LENGTH_SHORT).show();

                createUserWithEmailAndPassword(task -> {
                    if (task.isSuccessful()) {
                        saveAccountType(false);
                        Toast.makeText(RegistrationActivity.this, "account created,please wait as we initialise your account", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) return;
                        Technician technician = new Technician(name, repairService);
                        technician.setBusinessName(businessName);
                        technician.setLocation(((EditText) findViewById(R.id.location)).getText().toString());
                        technician.setPhone(((EditText) findViewById(R.id.phone)).getText().toString());
                        technician.setEmail(email.getText().toString());
                        technician.setUserID(user.getUid());
                        firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).document(user.getUid())
                                .set(technician).addOnCompleteListener(RegistrationActivity.this, task1 -> {
                                    if (task1.isSuccessful()) {
                                        startActivity(RegistrationActivity.this, TechnicianActivity.class);
                                        finish();
                                    }else showTaskException(task1,this);
                                });

                    } else showTaskException(task, this);
                });
            });
        });

        findViewById(R.id.login_client).setOnClickListener(c -> {
            createUserWithEmailAndPassword(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "account created,please wait as we initialise your account", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) return;
                    Client client = new Client();
                    client.setLocation(((EditText) findViewById(R.id.location)).getText().toString());
                    client.setPhone(((EditText) findViewById(R.id.phone)).getText().toString());
                    client.setEmail(email.getText().toString());
                    client.setUserID(user.getUid());
                    String email1 = user.getEmail();
                    if (email1 == null) return;
                    firebaseFirestore.collection(Constants.CLIENT_COLLECTION)
                            .document(user.getUid()).set(client).addOnCompleteListener(RegistrationActivity.this, task1 -> {
                                if (task1.isSuccessful()) {
                                    startActivity(this, DashboardActivity.class);
                                    finish();
                                }else showTaskException(task1,this);
                            });
                } else {
                    showTaskException(task, this);
                }
            });
        });
    }

    private void createUserWithEmailAndPassword(OnCompleteListener<AuthResult> onCompleteListener) {
        if (email.getText() == null || password.getText() == null) {
            Toast.makeText(this, "You must include email or password", Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        if (!emailPattern.matcher(email.getText()).matches()) {
            Toast.makeText(this, "You must provide a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().length() < 6) {
            Toast.makeText(this, "Your password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(RegistrationActivity.this, onCompleteListener);
    }
}