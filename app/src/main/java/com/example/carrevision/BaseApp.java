package com.example.carrevision;

import android.app.Application;

import com.example.carrevision.database.AppDatabase;
import com.example.carrevision.database.repository.CarRepository;

/**
 * Base application class
 */
public class BaseApp extends Application {
    /**
     * Gets the database instance
     * @return Database instance
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }
    /**
     * Gets the car repository
     * @return Car repository
     */
    public CarRepository getCarRepository() {
        return CarRepository.getInstance();
    }
}
