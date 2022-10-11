package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.BrandEntity;

@Dao
public interface BrandDao {
    @Insert
    void insert(BrandEntity brand) throws SQLiteConstraintException;
    @Query("DELETE FROM brands")
    void deleteAll();
}
