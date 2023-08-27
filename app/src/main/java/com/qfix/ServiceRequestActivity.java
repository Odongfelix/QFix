package com.qfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestActivity extends AppCompatActivity implements ExceptionHandler, Starter {
    private EditText name, model, manufacturer, details;
    private Client thisClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        name = findViewById(R.id.device_name);
        model = findViewById(R.id.phone);
        manufacturer = findViewById(R.id.location);
        details = findViewById(R.id.password);
        findViewById(R.id.book_now_home).setOnClickListener(b -> bookNow());
        findViewById(R.id.book_now_shop).setOnClickListener(b -> bookNow());
        findViewById(R.id.filter).setOnClickListener(f -> new HelpDialog(this));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        FirebaseFirestore.getInstance().collection(Constants.CLIENT_COLLECTION)
                .document(currentUser.getUid())
                .get().addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        thisClient = task.getResult().toObject(Client.class);
                    } else showTaskException(task, ServiceRequestActivity.this);
                });
    }

    private void bookNow() {
        if (thisClient == null) {
            Toast.makeText(this, "There is a lot of friction connecting to the server", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, BookNowActivity.class);
        Electronic electronic = new Electronic();
        electronic.setName(name.getText().toString());
        electronic.setModel(model.getText().toString());
        electronic.setManufacturer(manufacturer.getText().toString());
        electronic.setDetails(details.getText().toString());
        Job job = new Job();
        job.setElectronic(electronic);
        job.setClient(thisClient);
        intent.putExtra("job", job);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(this, DashboardActivity.class);
        super.onBackPressed();
    }
}