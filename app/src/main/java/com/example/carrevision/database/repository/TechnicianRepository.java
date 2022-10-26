package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.entity.TechnicianEntity;

import java.util.List;

/**
 * Technician repository singleton
 */
public class TechnicianRepository {
    private static volatile TechnicianRepository instance;

    /**
     * Technician repository class private constructor
     */
    private TechnicianRepository() {}

    /**
     * Gets the technician repository instance
     * @return Technician repository instance
     */
    public static TechnicianRepository getInstance() {
        if (instance == null) {
            synchronized (TechnicianRepository.class) {
                if (instance == null) {
                    instance = new TechnicianRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all the technicians from the database
     * @param application Application
     * @return List with all the technicians
     */
    public LiveData<List<TechnicianEntity>> getTechnicians(Application application) {
        return ((BaseApp) application).getDatabase().technicianDao().getAll();
    }
}
