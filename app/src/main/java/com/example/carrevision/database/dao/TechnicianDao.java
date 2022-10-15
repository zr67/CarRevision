package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.TechnicianEntity;

import java.util.List;

/**
 * Dao interface to interact with the table technicians in the database
 */
@Dao
public interface TechnicianDao {
    /**
     * Gets a technician by its unique identifier
     * @param id Technician's unique identifier
     * @return TechnicianEntity corresponding to the identifier
     */
    @Query("SELECT * FROM technicians WHERE id = :id")
    LiveData<TechnicianEntity> getById(int id);

    /**
     * Gets all technicians
     * @return List of all TechnicianEntity
     */
    @Query("SELECT * FROM technicians")
    LiveData<List<TechnicianEntity>> getAll();

    /**
     * Insert a new technician into the database
     * @param technician TechnicianEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(TechnicianEntity technician) throws SQLiteConstraintException;

    /**
     * Removes all technicians from the database
     */
    @Query("DELETE FROM technicians")
    void deleteAll();
}
