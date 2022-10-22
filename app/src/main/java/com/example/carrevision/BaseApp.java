package com.example.carrevision;

import android.app.Application;

import com.example.carrevision.database.AppDatabase;
import com.example.carrevision.database.repository.CantonRepository;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.database.repository.TechnicianRepository;

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

    /**
     * Gets the revision repository
     * @return Revision repository
     */
    public RevisionRepository getRevisionRepository() {
        return RevisionRepository.getInstance();
    }

    /**
     * Gets the canton repository
     * @return Canton repository
     */
    public CantonRepository getCantonRepository() {
        return CantonRepository.getInstance();
    }

    /**
     * Gets the technician repository
     * @return Technician repository
     */
    public TechnicianRepository getTechnicianRepository() {
        return TechnicianRepository.getInstance();
    }
}
