package com.example.uts_pbp.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.uts_pbp.room.dao.JadwalDao;
import com.example.uts_pbp.entity.Jadwal;

@Database(entities = {Jadwal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JadwalDao jadwalDao();

}
