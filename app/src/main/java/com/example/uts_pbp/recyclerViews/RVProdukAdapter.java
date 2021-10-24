package com.example.uts_pbp.recyclerViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uts_pbp.Dummy.DaftarProduk;
import com.example.uts_pbp.entity.Produk;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.RvItemProdukBinding;

import java.util.ArrayList;

public class RVProdukAdapter extends RecyclerView.Adapter<RVProdukAdapter.ViewHolder>{
    Context context;
    ArrayList<Produk> listProduk;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RvItemProdukBinding binding;

        public ViewHolder(@NonNull RvItemProdukBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this::onClick);
        }
        public void bind(Produk item) {
            binding.setPrdk(item);
            binding.executePendingBindings();
        }
        public void onClick(View view) {
            onNoteClick(getAdapterPosition());
        }

    }

    public void onNoteClick (int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.allert_produk);
//        String.valueOf(double d);

        ImageView image = dialog.findViewById(R.id.image);
        Produk.loadImage(image,listProduk.get(position).getImgUrl());
        TextView text1=dialog.findViewById(R.id.tv_detail_nama);
        text1.setText(listProduk.get(position).getNama());
        TextView text2=dialog.findViewById(R.id.tv_detail_harga);
//        text2.setText(listProduk.get(position).getHarga());
        TextView text3=dialog.findViewById(R.id.tv_detail_Deskripsi);
        text3.setText(listProduk.get(position).getDeskripsi());


        TextView text4= dialog.findViewById(R.id.button_detail_tutup);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public RVProdukAdapter(ArrayList<Produk> listProduk, Context context) {
        this.listProduk = listProduk;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemProdukBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_produk, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk item = listProduk.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() { return listProduk.size();}
}