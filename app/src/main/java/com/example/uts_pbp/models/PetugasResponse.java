package com.example.uts_pbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PetugasResponse {
    private String message;

    @SerializedName("data")
    private ArrayList<Petugas> petugasList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Petugas> getPetugasList() {
        return petugasList;
    }

    public void setPetugasList(ArrayList<Petugas> petugasList) {
        this.petugasList = petugasList;
    }
}
