package com.example.carrevision;

import android.app.Application;

import com.example.carrevision.database.AppDatabase;

/**
 * Base application class
 */
public abstract class BaseApp extends Application {
    /**
     * Gets the database instance
     * @return Database instance
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }
    // REPOSITORIES!!!
}
