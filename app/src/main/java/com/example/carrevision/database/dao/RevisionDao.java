package com.example.carrevision.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.pojo.CompleteRevision;

import java.util.List;

/**
 * Dao interface to interact with the table revisions in the database
 */
@Dao
public interface RevisionDao {
    /**
     * Gets a revision by its unique identifier
     * @param id Revision's unique identifier
     * @return RevisionEntity corresponding to the identifier
     */
    @Transaction
    @Query("SELECT * FROM revisions WHERE id = :id")
    LiveData<CompleteRevision> getById(int id);

    /**
     * Gets all revisions
     * @return List of all RevisionEntity
     */
    @Transaction
    @Query("SELECT * FROM revisions")
    LiveData<List<CompleteRevision>> getAll();

    /**
     * Insert a new revision into the database
     * @param revision RevisionEntity to insert
     * @throws SQLiteConstraintException Exception thrown if constraints are not matched
     */
    @Insert
    void insert(RevisionEntity revision) throws SQLiteConstraintException;

    /**
     * Updates a revision existing into the database
     * @param revision RevisionEntity to update
     */
    @Update
    void update(RevisionEntity revision);

    /**
     * Removes a revision from the database by its unique identifier
     * @param id Revision's unique identifier
     */
    @Query("DELETE FROM revisions WHERE id = :id")
    void deleteById(int id);

    /**
     * Removes all revisions from the database
     */
    @Query("DELETE FROM revisions")
    void deleteAll();
}
