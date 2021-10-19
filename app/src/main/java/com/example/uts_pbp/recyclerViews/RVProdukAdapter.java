package com.example.uts_pbp.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

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
        }
        public void bind(Produk item) {
            binding.setPrdk(item);
            binding.executePendingBindings();
        }
    }

    public RVProdukAdapter(ArrayList<Produk> listProduk, Context context) {
        this.listProduk = listProduk;
        this.context = context;
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
