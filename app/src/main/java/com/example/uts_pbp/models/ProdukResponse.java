package com.example.uts_pbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukResponse {
    private String message;

    @SerializedName("data")
    private ArrayList<Produk> produkList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Produk> getProdukList() {
        return produkList;
    }

    public void setProdukList(ArrayList<Produk> produkList) {
        this.produkList = produkList;
    }
}
