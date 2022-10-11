package database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import database.entity.ModelEntity;

@Dao
public interface ModelDao {
    @Insert
    void insert(ModelEntity model) throws SQLiteConstraintException;
    @Query("DELETE FROM models")
    void deleteAll();
}
