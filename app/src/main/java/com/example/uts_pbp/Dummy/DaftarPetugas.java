package com.example.uts_pbp.Dummy;

import com.example.uts_pbp.entity.Petugas;

import java.util.ArrayList;

public class DaftarPetugas {
    public ArrayList<Petugas> listPetugas;
    public DaftarPetugas () {
        listPetugas = new ArrayList();
        listPetugas.add(new Petugas("Bapak Jago","https://cdn.discordapp.com/attachments/615164305817468958/890964403485278258/joseph-gonzalez-iFgRcqHznqg-unsplash.jpg"));
        listPetugas.add(new Petugas("Ibu Mahir", "https://cdn.discordapp.com/attachments/615164305817468958/890964403485278258/joseph-gonzalez-iFgRcqHznqg-unsplash.jpg"));
    }
}
