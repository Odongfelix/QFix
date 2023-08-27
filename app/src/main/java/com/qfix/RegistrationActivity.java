package com.qfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class RegistrationActivity extends AccountActivity implements Starter, ExceptionHandler, Text {

    private EditText email, password;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    private GoogleSignInClient googleSignInClient;
    private int signInCode = 1;
    private boolean clientAccount;
    private Technician googleTechnician;
    private Client googleClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        findViewById(R.id.alt).setOnClickListener(v -> new AccountTypeDialog(RegistrationActivity.this).setListener(new OnAccountTypeSelectedListener() {
            @Override
            public void onTechnicianAccountSelected() {
                clientAccount = false;
                saveAccountType(false);
                new RegisterAsTechnicianDialog(RegistrationActivity.this).setOnRegisterListener(
                        new OnRegisterListener() {
                            @Override
                            public void onRegister(String name, String businessName, String repairService) {
                                if (isEmpty(name) || isEmpty(businessName) || isEmpty(repairService)) {
                                    Toast.makeText(RegistrationActivity.this, "You must fill all fields.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                googleTechnician = new Technician(name, repairService);
                                googleTechnician.setBusinessName(businessName);
                                new LocationDialog(RegistrationActivity.this).setListener(location -> {
                                    if (isEmpty(location)) {
                                        Toast.makeText(RegistrationActivity.this, "You must add Location", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    googleTechnician.setLocation(location);
                                    startActivityForResult(googleSignInClient.getSignInIntent(), signInCode);
                                });
                            }
                        }
                );
            }

            @Override
            public void onClientAccountSelected() {
                clientAccount = true;
                new LocationDialog(RegistrationActivity.this).setListener(location -> {
                    if (isEmpty(location)) {
                        Toast.makeText(RegistrationActivity.this, "Your location is used to suggest technicians near you", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    googleClient = new Client();
                    googleClient.setLocation(location);
                    startActivityForResult(googleSignInClient.getSignInIntent(), signInCode);
                });
            }
        }));


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        /*try {
            firebaseAuth.useEmulator("10.0.2.2", 9099);
            firebaseFirestore.useEmulator("10.0.2.2", 8080);
        } catch (Exception ignored) {

        }*/


        findViewById(R.id.login).setOnClickListener(l -> {
            new RegisterAsTechnicianDialog(RegistrationActivity.this).setOnRegisterListener((name, businessName, repairService) -> {

                createUserWithEmailAndPassword(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "account created,please wait as we initialise your account", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) return;
                        Technician technician = new Technician(name, repairService);
                        technician.setBusinessName(businessName);
                        technician.setLocation(((EditText) findViewById(R.id.location)).getText().toString());
                        technician.setPhone(((EditText) findViewById(R.id.phone)).getText().toString());
                        technician.setEmail(email.getText().toString());
                        technician.setUserID(user.getUid());
                        saveTechnician(user, technician);
                    } else showTaskException(task, this);
                });
            });
        });

        findViewById(R.id.login_client).setOnClickListener(c -> {
            createUserWithEmailAndPassword(task -> {
                progressDialog.dismiss();
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
                                } else showTaskException(task1, this);
                            });
                } else {
                    showTaskException(task, this);
                }
            });
        });
    }

    private void saveTechnician(FirebaseUser user, Technician technician) {
        firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).document(user.getUid())
                .set(technician).addOnCompleteListener(RegistrationActivity.this, task1 -> {
                    if (task1.isSuccessful()) {
                        saveAccountType(false);
                        startActivity(RegistrationActivity.this, TechnicianActivity.class);
                        finish();
                    } else showTaskException(task1, this);
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
        progressDialog = new ProgressDialog(this);
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(RegistrationActivity.this, onCompleteListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == signInCode && resultCode == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount result = task.getResult();
            AuthCredential authCredential = GoogleAuthProvider.getCredential(result.getIdToken(), null);
            if (clientAccount) registerClient(authCredential);
            else registerTechnician(authCredential);
        } else
            Toast.makeText(this, "Process failed due to unKnown error", Toast.LENGTH_SHORT).show();
    }//https://quickfix-ab26a.firebaseapp.com/__/auth/handler

    private void registerTechnician(AuthCredential authCredential) {
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful())
                saveTechnician(authCredential, googleTechnician);
            else showTaskException(task, RegistrationActivity.this);

        });
    }

    private void saveTechnician(AuthCredential authCredential, Technician googleTechnician) {
        saveAccountType(false);
        firebaseFirestore.collection(Constants.TECHNICIAN_COLLECTION).document(authCredential.getProvider())
                .set(googleTechnician).addOnCompleteListener(RegistrationActivity.this, task1 -> {
                    if (task1.isSuccessful()) {
                        startActivity(RegistrationActivity.this, TechnicianActivity.class);
                        finish();
                    } else showTaskException(task1, this);
                });
    }

    private void registerClient(AuthCredential authCredential) {
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Toast.makeText(this, "Couldn't get information about you from google", Toast.LENGTH_SHORT).show();
                    return;
                }
                googleClient.setUserID(user.getUid());
                FirebaseFirestore.getInstance().collection(Constants.CLIENT_COLLECTION).document(user.getUid())
                        .set(googleClient).addOnCompleteListener(RegistrationActivity.this,
                                task1 -> {
                                    if (task1.isSuccessful()) {
                                        saveAccountType(true);
                                        startActivity(RegistrationActivity.this, DashboardActivity.class);
                                    } else showTaskException(task1, RegistrationActivity.this);
                                });
            } else showTaskException(task, this);
        });
    }

}