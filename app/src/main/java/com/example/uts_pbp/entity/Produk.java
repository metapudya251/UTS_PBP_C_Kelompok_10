package com.example.uts_pbp.entity;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Produk {
    private String nama, deskripsi, imgUrl;
    private double harga;

    public Produk (String nama, String deskripsi, double harga, String imgUrl) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.imgUrl = imgUrl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgURL) {
        this.imgUrl = imgURL;
    }

    @BindingAdapter("loadImg")
    public static void loadImage(ImageView view, String imgUrl) {
        Glide.with(view.getContext())
                .load(imgUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
