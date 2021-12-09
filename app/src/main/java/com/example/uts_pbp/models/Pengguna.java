package com.example.uts_pbp.models;

public class Pengguna {
    private int id;
    private String name, email, password, token;
    //penamaan kacau, ini untuk menyimpan String value base64 image user. Hati" dalam pembacaan kode!
    private String imgUrl;

    public Pengguna () {}

    public Pengguna(int id, String name, String email, String password, String imgUrl, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
