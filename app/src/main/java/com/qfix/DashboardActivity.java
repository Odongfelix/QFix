package com.qfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardActivity extends TechnicianActivity implements Starter {

    private String emptyService;

    @Override
    protected void changeTabs(View[] tabs) {
        for (int i = 0; i < tabs.length; i++) {
            if (i == 0) {
                LinearLayout l = (LinearLayout) tabs[i];
                for (int j = 0; j < l.getChildCount(); j++) {
                    View v = l.getChildAt(j);
                    if (v instanceof ImageView) {
                        ((ImageView) v).setImageResource(R.drawable.baseline_access_time_24);
                    }
                    if (v instanceof TextView) {
                        ((TextView) v).setText("Completed");
                    }
                }
            }

            if (i == 2) {
                LinearLayout l = (LinearLayout) tabs[i];
                for (int j = 0; j < l.getChildCount(); j++) {
                    View v = l.getChildAt(j);
                    if (v instanceof ImageView) {
                        ((ImageView) v).setImageResource(R.drawable.baseline_block_24);
                    }
                    if (v instanceof TextView) {
                        ((TextView) v).setText("Rejected");
                    }
                }
            }
        }

    }

    @Override
    protected void tabClicked(int index) {
        if (index == 2) {
            adapter.setEmptyImage(R.drawable.outline_emoji_emotions_24);
            adapter.setEmptyJobText("You don't have any rejected service request");
            adapter.notifyItemChanged(0);
            return;
        }
        adapter.setEmptyImage(R.drawable.outline_remove_shopping_cart_24);
        adapter.setEmptyJobText(emptyService);
        adapter.notifyItemChanged(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View add = findViewById(R.id.add);
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(v -> startActivity(this, ServiceRequestActivity.class));

        emptyService = getString(R.string.empty_service);

        adapter.setEmptyImage(R.drawable.outline_remove_shopping_cart_24);
        adapter.setEmptyJobText(emptyService);
        setTitle("Repair status");
    }
}