package com.example.uts_pbp.models;

public class Petugas {
    private String nama, imgUrl;

    public Petugas(String nama, String imgUrl) {
        this.nama = nama;
        this.imgUrl = imgUrl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
