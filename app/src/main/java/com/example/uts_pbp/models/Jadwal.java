package com.example.uts_pbp.models;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.uts_pbp.BR;

public class Jadwal extends BaseObservable {
    private int id;


    private String nama;
    private String telp;
    private String tanggal;
    private String pelayanan;
    private String petugas;
    private String imgUrl;

    public Jadwal () {}

    public Jadwal(int id, String nama, String telp, String tanggal, String pelayanan, String petugas, String imgUrl) {
        this.id = id;
        this.nama = nama;
        this.telp = telp;
        this.tanggal = tanggal;
        this.pelayanan = pelayanan;
        this.petugas = petugas;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    @Bindable
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
        notifyPropertyChanged(BR.tanggal);
    }

    public String getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(String pelayanan) {
        this.pelayanan = pelayanan;
    }

    public String getPetugas() {
        return petugas;
    }

    public void setPetugas(String petugas) {
        this.petugas = petugas;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @BindingAdapter("loadImg")
    public static void loadImage(ImageView view, String imgUrl) {
        Glide.with(view.getContext())
                .load(imgUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
