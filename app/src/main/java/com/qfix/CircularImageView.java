package com.qfix;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Image view for displaying circular images
 * This code is subject to my terms -> you can
 * use as is without redistribution
 */

public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {
    public CircularImageView(Context context) {
        super(context);
    }
    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null) {
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(()->{
            RoundedBitmapDrawable r = RoundedBitmapDrawableFactory.create(getResources(), bm);
            r.setCircular(true);
            handler.post(()->{
                setImageDrawable(r);
                setVisibility(VISIBLE);
            });
        }).start();
    }
}
