package database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import database.entity.CarEntity;

@Dao
public interface CarDao {
    @Insert
    void insert(CarEntity car) throws SQLiteConstraintException;
    @Query("DELETE FROM cars")
    void deleteAll();
}
