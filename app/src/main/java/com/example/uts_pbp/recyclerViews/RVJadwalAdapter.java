package com.example.uts_pbp.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.RvItemJadwalBinding;
import com.example.uts_pbp.entity.Jadwal;

import java.util.List;

public class RVJadwalAdapter extends RecyclerView.Adapter<RVJadwalAdapter.ViewHolder>{
    Context context;
    List<Jadwal> listJadwal;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RvItemJadwalBinding binding;

        public ViewHolder(@NonNull RvItemJadwalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Jadwal item) {
            binding.setJdwl(item);
            binding.executePendingBindings();
        }
    }

    public RVJadwalAdapter(List<Jadwal> listJadwal, Context context) {
        this.listJadwal = listJadwal;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVJadwalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemJadwalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_jadwal, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Jadwal item = listJadwal.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }
}
