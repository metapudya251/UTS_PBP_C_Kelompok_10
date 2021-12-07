package com.example.uts_pbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JadwalResponse {
    private String message;

    @SerializedName("data")
    private ArrayList<Jadwal> jadwalList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Jadwal> getJadwalList() {
        return jadwalList;
    }

    public void setJadwalList(ArrayList<Jadwal> jadwalList) {
        this.jadwalList = jadwalList;
    }
}
