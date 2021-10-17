package com.example.uts_pbp.RecyclerViews;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVJadwalAdapter extends RecyclerView.Adapter<RVJadwalAdapter.viewHolder>{

    public class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            //plugin tampilan
        }
    }

    public RVJadwalAdapter() {

    }

    @NonNull
    @Override
    public RVJadwalAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RVJadwalAdapter.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
