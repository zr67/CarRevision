package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.CantonEntity;

import java.util.List;

/**
 * Dao interface to interact with the table cantons in the database
 */
@Dao
public interface CantonDao {
    /**
     * Gets a canton by its unique identifier
     * @param id Canton's unique identifier
     * @return CantonEntity corresponding to the identifier
     */
    @Query("SELECT * FROM cantons WHERE id = :id")
    LiveData<CantonEntity> getById(int id);

    /**
     * Gets all cantons
     * @return List of all CantonEntity
     */
    @Query("SELECT * FROM cantons")
    LiveData<List<CantonEntity>> getAll();

    /**
     * Insert a new canton into the database
     * @param canton CantonEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(CantonEntity canton) throws SQLiteConstraintException;

    /**
     * Removes all cantons from the database
     */
    @Query("DELETE FROM cantons")
    void deleteAll();
}
