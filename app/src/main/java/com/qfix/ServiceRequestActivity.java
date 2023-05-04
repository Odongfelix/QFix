package com.qfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;

public class ServiceRequestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int spanCount = Math.round(metrics.widthPixels
                /
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        120, metrics));
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, spanCount);
        ArrayList<Technician> results = new ArrayList<>();
        SearchAdapter adapter = new SearchAdapter(results);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        new Thread(() -> {
            Technician t = new Technician("I-Man", "iPhone repairs");
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.mipmap.test);
            int hSize = Math.min(op.outHeight, op.outWidth) / 2, sampleSize = 1,
                    reqSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            while (hSize / sampleSize >= reqSize) {
                sampleSize *= 2;
            }
            op.inJustDecodeBounds = false;
            op.inSampleSize = sampleSize;
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.test));
            t.setStars(3);
            results.add(t);
            t = new Technician("Android Man","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.test2));
            t.setStars(5);
            results.add(t);
            t = new Technician("Soft Man","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.fix));
            t.setStars(1);
            results.add(t);
            t = new Technician("Lucky CPU","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.test));
            t.setStars(2);
            results.add(t);
            t = new Technician("One Felix","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.test2));
            t.setStars(3);
            results.add(t);
            t = new Technician("John Doe","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.fix));
            t.setStars(4);
            results.add(t);
            t = new Technician("Little Man","Software installation");
            t.setDp(BitmapFactory.decodeResource(getResources(), R.mipmap.test));
            t.setStars(5);
            results.add(t);
            new Handler(Looper.getMainLooper()).post(() -> {
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

}