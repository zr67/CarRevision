package com.example.carrevision.database;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
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
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

/**
 * Application database class
 */
@Database(entities = {BrandEntity.class, CantonEntity.class, CarEntity.class, ModelEntity.class, RevisionEntity.class, TechnicianEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DBNAME = "db-carrevisions";
    private static AppDatabase instance;
    private final MutableLiveData<Boolean> isDBCreated = new MutableLiveData<>();

    /**
     * Gets the brands dao interface
     * @return Brands dao interface
     */
    public abstract BrandDao brandDao();

    /**
     * Gets the cantons dao interface
     * @return Cantons dao interface
     */
    public abstract CantonDao cantonDao();

    /**
     * Gets the cars dao interface
     * @return Cars dao interface
     */
    public abstract CarDao carDao();

    /**
     * Gets the models dao interface
     * @return Models dao interface
     */
    public abstract ModelDao modelDao();

    /**
     * Gets the revisions dao interface
     * @return Revisions dao interface
     */
    public abstract RevisionDao revisionDao();

    /**
     * Gets the technicians dao interface
     * @return Technicians dao interface
     */
    public abstract TechnicianDao technicianDao();

    /**
     * Gets the AppDatabase instance
     * @param context Context
     * @return AppDatabase instance
     */
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

    /**
     * Builds the application database
     * @param context Context
     * @return AppDatabase instance
     */
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

    /**
     * Update status when the database is created
     * @param context Context
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DBNAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDBCreated();
        }
    }

    /**
     * Sets the database status to created
     */
    private void setDBCreated() {
        isDBCreated.postValue(true);
    }

    /**
     * Gets the database status
     * @return Database status
     */
    public LiveData<Boolean> getDBCreated() {
        return isDBCreated;
    }
}