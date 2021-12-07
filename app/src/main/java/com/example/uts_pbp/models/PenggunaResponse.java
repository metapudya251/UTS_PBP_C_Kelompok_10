package com.example.uts_pbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PenggunaResponse {
    private String message;

    @SerializedName("data")
    private ArrayList<Pengguna> penggunaList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Pengguna> getPenggunaList() {
        return penggunaList;
    }

    public void setPenggunaList(ArrayList<Pengguna> penggunaList) {
        this.penggunaList = penggunaList;
    }
}
