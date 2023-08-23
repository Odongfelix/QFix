package com.qfix;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private final CircularImageView circularImageView;
    private final LinearLayout reviews;
    private final TextView name;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.phone_);
        circularImageView = itemView.findViewById(R.id.dp);
        reviews = itemView.findViewById(R.id.reviews);
    }

    public void set(Technician t) {
        name.setText(t.getName());
        circularImageView.setImageBitmap(t.getDp());
        setStars(t.getStars());
    }

    private void setStars(int stars) {
        for (int i = 0; i < 5; i++) {
            if (i<stars) {
                ((ImageView)reviews.getChildAt(i)).setImageResource(R.drawable.message_star);
                continue;
            }
            ((ImageView)reviews.getChildAt(i)).setImageDrawable(null);
        }
    }
}
