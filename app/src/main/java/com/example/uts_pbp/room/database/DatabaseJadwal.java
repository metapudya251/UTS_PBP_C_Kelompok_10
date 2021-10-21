package com.example.uts_pbp.room.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseJadwal {
    private Context context;
    private static DatabaseJadwal databaseJadwal;

    private AppDatabase database;

    public DatabaseJadwal(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "jadwal").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseJadwal getInstance(Context context){
        if(databaseJadwal == null){
            databaseJadwal = new DatabaseJadwal(context);
        }
        return databaseJadwal;
    }

    public AppDatabase getDatabase() {return database; }
}
