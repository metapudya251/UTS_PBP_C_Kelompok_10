package com.example.uts_pbp.recyclerViews;

import android.app.Dialog;
import android.content.Context;
//import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.models.Produk;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.RvItemProdukBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RVProdukAdapter extends RecyclerView.Adapter<RVProdukAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Produk> listProduk;


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

        public void onClick (View view){
            onNoteClick(getAdapterPosition());
        }
    }

    //pemanggil tampilan detail produk
    public void onNoteClick (int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_produk);

        TextView text1 = dialog.findViewById(R.id.tv_detail_nama);
        text1.setText(listProduk.get(position).getNama());
        TextView text2 = dialog.findViewById(R.id.tv_detail_harga);
        text2.setText(String.valueOf(BigDecimal.valueOf(listProduk.get(position).getHarga()).setScale(2, RoundingMode.HALF_UP)));
        TextView text3 = dialog.findViewById(R.id.tv_detail_deskripsi);
        text3.setText(listProduk.get(position).getDeskripsi());
        ImageView image = dialog.findViewById(R.id.image_detail_produk);
        Produk.loadImage(image, listProduk.get(position).getImgUrl());

        TextView text4 = dialog.findViewById(R.id.button_detail_tutup);
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

    public void setProdukList(ArrayList<Produk> listProduk) {
        this.listProduk = listProduk;
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