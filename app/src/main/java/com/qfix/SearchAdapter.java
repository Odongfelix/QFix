package com.qfix;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private ArrayList<Technician> results;

    public SearchAdapter(ArrayList<Technician> r) {
        results = r;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(
                LayoutInflater.from(parent.getContext()
                ).inflate(R.layout.row_search,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Technician t = results.get(position);
        holder.set(t);
        holder.itemView.setOnClickListener(i -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, CreateServiceActivity.class);
            //dont forget to put some data in this 'intent' aha aha in squipards voice
            intent.putExtra(Constants.NAME,t.getName());
            intent.putExtra(Constants.REPAIR_SERVICE,t.getRepairService());
            //todo, in the release version, this should be a link to this technician
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
