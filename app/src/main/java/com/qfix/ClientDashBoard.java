package com.qfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Activity for showing the client dash board
 * implementation details are straight forward
 */
public class ClientDashBoard extends AppCompatActivity {
    private View content, drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash_board);
        findViewById(R.id.cart).setOnClickListener(v -> {
            startActivity(new Intent(ClientDashBoard.this, BookingManagementActivity.class));
        });
        findViewById(R.id.menu).setOnClickListener(m -> {
            drawer.setVisibility(View.VISIBLE);
            content.setBackgroundColor(Color.BLACK);
            content.setAlpha(.2f);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            ValueAnimator anim = ValueAnimator.ofFloat(0, 0).setDuration(500);
            anim.start();
            anim.addUpdateListener(a -> {
                drawer.getLayoutParams().width = Math.round(width * a.getAnimatedFraction());
                drawer.requestLayout();
            });
        });
        findViewById(R.id.cancel).setOnClickListener(c -> {
            drawer.setVisibility(View.GONE);
            content.setBackgroundColor(Color.TRANSPARENT);
            content.setAlpha(1f);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            ValueAnimator anim = ValueAnimator.ofFloat(0, 0).setDuration(500);
            anim.start();
            anim.addUpdateListener(a -> {
                content.getLayoutParams().width = Math.round(width * a.getAnimatedFraction());
                content.requestLayout();
            });
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    content.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    content.requestLayout();
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {

                }
            });
        });
        content = findViewById(R.id.content);
        drawer = findViewById(R.id.drawer);
        findViewById(R.id.customer_support).setOnClickListener(s->{
            startActivity(new Intent(ClientDashBoard.this,CustomerSupport.class));
        });
    }
}