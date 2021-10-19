package com.example.uts_pbp.Dummy;

import com.example.uts_pbp.entity.Produk;

import java.util.ArrayList;

public class DaftarProduk {
    public ArrayList<Produk> listProduk;
    public DaftarProduk () {
        listProduk = new ArrayList();
        listProduk.add(new Produk ("Potong Rambut", "Isi deskripsi potong rambut", 15.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
        listProduk.add(new Produk ("Cream Bath", "Isi deskripsi cream bath", 25.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
        listProduk.add(new Produk ("Coloring", "Isi deskripsi coloring", 30.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
        listProduk.add(new Produk ("Facial Treatment", "Isi deskripsi facial treatment", 145.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
        listProduk.add(new Produk ("Smoothing", "Isi deskripsi potong smoothing", 125.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
        listProduk.add(new Produk ("Body Spa", "Isi deskripsi potong body spa", 235.000, "https://cdn.discordapp.com/attachments/615164305817468958/899819742892802048/unknown.png"));
    };
}
