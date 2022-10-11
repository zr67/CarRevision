package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.TechnicianEntity;

@Dao
public interface TechnicianDao {
    @Insert
    void insert(TechnicianEntity technician) throws SQLiteConstraintException;
    @Query("DELETE FROM technicians")
    void deleteAll();
}
