package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrevision.database.entity.CarEntity;

import java.util.List;

/**
 * Dao interface to interact with the table cars in the database
 */
@Dao
public interface CarDao {
    /**
     * Gets a car by its unique identifier
     * @param id Car's unique identifier
     * @return CarEntity corresponding to the identifier
     */
    @Query("SELECT * FROM cars WHERE id = :id")
    LiveData<CarEntity> getById(int id);

    /**
     * Gets all cars
     * @return List of all CarEntity
     */
    @Query("SELECT * FROM cars")
    LiveData<List<CarEntity>> getAll();

    /**
     * Insert a new car into the database
     * @param car CarEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(CarEntity car) throws SQLiteConstraintException;

    /**
     * Updates a car existing into the database
     * @param car CarEntity to update
     */
    @Update
    void update(CarEntity car);

    /**
     * Removes a car from the database by its unique identifier
     * @param id Car's unique identifier
     */
    @Query("DELETE FROM cars WHERE id = :id")
    void deleteById(int id);

    /**
     * Removes all cars from the database
     */
    @Query("DELETE FROM cars")
    void deleteAll();
}
