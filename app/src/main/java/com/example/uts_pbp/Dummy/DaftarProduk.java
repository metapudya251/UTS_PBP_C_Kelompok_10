package com.example.uts_pbp.Dummy;

import com.example.uts_pbp.entity.Produk;

import java.util.ArrayList;

public class DaftarProduk {
    public ArrayList<Produk> listProduk;
    public DaftarProduk () {
        listProduk = new ArrayList();
        listProduk.add(new Produk ("Potong Rambut", "Suatu hal yang digunakan untuk membentuk tampilan rambut.", 15000, "https://cdn.discordapp.com/attachments/615164305817468958/901767266708889600/162-1627402_scissor-and-comb-vector-scissor-and-comb-png.png"));
        listProduk.add(new Produk ("Creambath", "Perawatan rambut dan kulit kepala untuk melembabkan rambut yang kering.", 25000, "https://cdn.discordapp.com/attachments/615164305817468958/901767054066081802/depositphotos_468520562-stock-illustration-bottles-lotion-cream-moisturizer-flat.png"));
        listProduk.add(new Produk ("Coloring", "Metode yang digunakan untuk memberikan warna pada rambut.", 30000, "https://cdn.discordapp.com/attachments/615164305817468958/901766335904772096/unknown.png"));
        listProduk.add(new Produk ("Facial Treatment", "Perawatan wajah yang digunakan untuk peremajaan kulit.", 145000, "https://cdn.discordapp.com/attachments/615164305817468958/901766172939284490/unknown.png"));
        listProduk.add(new Produk ("Smoothing", "Teknik meluruskan rambut menggunakan obat yang mengandung keratin.", 125000, "https://cdn.discordapp.com/attachments/615164305817468958/901765925857026058/unknown.png"));
        listProduk.add(new Produk ("Body Spa", "Perawatan yang ditunjukan untuk relaksasi tubuh.", 235000, "https://cdn.discordapp.com/attachments/615164305817468958/901765468812087306/unknown.png"));
    };
}
