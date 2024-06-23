package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Activity for requesting a repair service
 */
public class CreateServiceActivity extends AppCompatActivity {
    private CircularImageView iv;
    private TextView name, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        iv = findViewById(R.id.dp);
        name = findViewById(R.id.phone_);
        title = findViewById(R.id.job);
        Intent i = getIntent();
        name.setText(i.getStringExtra(Constants.NAME));
        title.setText(i.getStringExtra(Constants.REPAIR_SERVICE));
        new Thread(() -> {
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.mipmap.test);
            int hSize = Math.min(op.outHeight, op.outWidth) / 2, sampleSize = 1,
                    reqSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
            while (hSize / sampleSize >= reqSize) {
                sampleSize *= 2;
            }
            op.inJustDecodeBounds = false;
            op.inSampleSize = sampleSize;
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.test, op);
            new Handler(Looper.getMainLooper()).post(() -> iv.setImageBitmap(b));

        }).start();
    }
}