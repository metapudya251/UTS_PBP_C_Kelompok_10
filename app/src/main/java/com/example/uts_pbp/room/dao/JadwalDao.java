package com.example.uts_pbp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.uts_pbp.entity.Jadwal;

import java.util.List;

@Dao
public interface JadwalDao {
    @Query("SELECT * FROM jadwal")
    List<Jadwal> getAll();

    @Insert
    void insertJadwal(Jadwal jadwal);

    @Update
    void updateJadwal(Jadwal jadwal);

    @Delete
    void deleteJadwal(Jadwal jadwal);
}
