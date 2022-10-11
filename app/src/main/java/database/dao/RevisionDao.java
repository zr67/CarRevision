package database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import database.entity.RevisionEntity;
@Dao
public interface RevisionDao {
    @Insert
    void insert(RevisionEntity revision) throws SQLiteConstraintException;
    @Query("DELETE FROM revisions")
    void deleteAll();
}
