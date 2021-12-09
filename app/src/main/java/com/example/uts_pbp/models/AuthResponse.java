package com.example.uts_pbp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AuthResponse {
    private String message;

    @SerializedName("user")
    private Pengguna user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pengguna getUser() {
        return user;
    }

    public void setUserList(Pengguna user) {
        this.user = user;
    }
}
