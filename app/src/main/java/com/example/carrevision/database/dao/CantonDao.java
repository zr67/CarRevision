package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.CantonEntity;

@Dao
public interface CantonDao {
    @Insert
    void insert(CantonEntity canton) throws SQLiteConstraintException;
    @Query("DELETE FROM cantons")
    void deleteAll();
}
