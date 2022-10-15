package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.BrandEntity;

import java.util.List;

/**
 * Dao interface to interact with the table brands in the database
 */
@Dao
public interface BrandDao {
    /**
     * Gets a brand by its unique identifier
     * @param id Brand's unique identifier
     * @return BrandEntity corresponding to the identifier
     */
    @Query("SELECT * FROM brands WHERE id = :id")
    LiveData<BrandEntity> getById(int id);

    /**
     * Gets all brands
     * @return List of all BrandEntity
     */
    @Query("SELECT * FROM brands")
    LiveData<List<BrandEntity>> getAll();

    /**
     * Insert a new brand into the database
     * @param brand BrandEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(BrandEntity brand) throws SQLiteConstraintException;

    /**
     * Removes all brands from the database
     */
    @Query("DELETE FROM brands")
    void deleteAll();
}
