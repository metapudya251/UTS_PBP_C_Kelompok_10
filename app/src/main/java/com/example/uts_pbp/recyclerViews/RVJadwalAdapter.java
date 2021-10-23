package com.example.uts_pbp.recyclerViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
            binding.getRoot().setOnClickListener(this::onClick);
        }
        public void bind(Jadwal item) {
            binding.setJdwl(item);
            binding.executePendingBindings();
        }

        public void onClick (View view){
            onNoteClick(getAdapterPosition());
        }
    }

    //pemanggil tampilan detail jadwal
    public void onNoteClick (int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_jadwal);

        TextView text1 = dialog.findViewById(R.id.tv_detail_tanggal);
        text1.setText(listJadwal.get(position).getTanggal());
        TextView text2 = dialog.findViewById(R.id.tv_detail_pelayanan);
        text2.setText(listJadwal.get(position).getPelayanan());
        TextView text3 = dialog.findViewById(R.id.tv_detail_petugas);
        text3.setText(listJadwal.get(position).getPetugas());
        ImageView image = dialog.findViewById(R.id.image_detail_petugas);
        Jadwal.loadImage(image, listJadwal.get(position).getImgUrl());

        TextView text4 = dialog.findViewById(R.id.button_detail_tutup);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
