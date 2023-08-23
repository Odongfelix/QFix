package com.qfix;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyJobHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;
    public EmptyJobHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.empty_text);
        imageView = view.findViewById(R.id.a);
    }

    public void set(String text) {
        textView.setText(text);
    }

    public void set(int id) {
        imageView.setImageResource(id);
    }
}
