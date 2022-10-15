package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.carrevision.database.entity.ModelEntity;

import java.util.List;

/**
 * Dao interface to interact with the table models in the database
 */
@Dao
public interface ModelDao {
    /**
     * Gets a model by its unique identifier
     * @param id Model's unique identifier
     * @return ModelEntity corresponding to the identifier
     */
    @Query("SELECT * FROM models WHERE id = :id")
    LiveData<ModelEntity> getById(int id);

    /**
     * Gets all models
     * @return List of all ModelEntity
     */
    @Query("SELECT * FROM models")
    LiveData<List<ModelEntity>> getAll();

    /**
     * Insert a new model into the database
     * @param model ModelEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(ModelEntity model) throws SQLiteConstraintException;

    /**
     * Removes all models from the database
     */
    @Query("DELETE FROM models")
    void deleteAll();
}
