package com.example.carrevision.database;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;

import com.example.carrevision.database.dao.BrandDao;
import com.example.carrevision.database.dao.CantonDao;
import com.example.carrevision.database.dao.CarDao;
import com.example.carrevision.database.dao.ModelDao;
import com.example.carrevision.database.dao.RevisionDao;
import com.example.carrevision.database.dao.TechnicianDao;
import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

@Database(entities = {BrandEntity.class, CantonEntity.class, CarEntity.class, ModelEntity.class, Readable.class, TechnicianEntity.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DBNAME = "db-carrevisions";
    private static AppDatabase instance;
    private final MutableLiveData<Boolean> isDBCreated = new MutableLiveData<>();

    public abstract BrandDao brandDao();
    public abstract CantonDao cantonDao();
    public abstract CarDao carDao();
    public abstract ModelDao modelDao();
    public abstract RevisionDao revisionDao();
    public abstract TechnicianDao technicianDao();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
    private static AppDatabase buildDatabase(final Context context) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(context, AppDatabase.class, DBNAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(context);
                            DBInitializer.populateDatabase(database);
                            database.setDBCreated();
                        });
                    }
                }).build();
    }
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DBNAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDBCreated();
        }
    }
    private void setDBCreated() {
        isDBCreated.postValue(true);
    }
    public LiveData<Boolean> getDBCreated() {
        return isDBCreated;
    }
}