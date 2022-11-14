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
     * Gets all models from a brand
     * @param id Brand's identifier
     * @return All models from the brand
     */
    @Query("SELECT * FROM models WHERE brand_id = :id")
    LiveData<List<ModelEntity>> getByBrandId(int id);

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
