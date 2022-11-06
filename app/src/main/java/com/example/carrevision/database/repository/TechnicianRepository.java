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

    /**
     * Gets a technician by it's identifier
     * @param application Application
     * @param technicianId Technician's identifier
     * @return Technician corresponding to the identifier
     */
    public LiveData<TechnicianEntity> getTechnician(Application application, final int technicianId) {
        return ((BaseApp) application).getDatabase().technicianDao().getById(technicianId);
    }

    /**
     * Gets a technician by it's email
     * @param application Application
     * @param email Technician's email
     * @return Technician corresponding to the email
     */
    public LiveData<TechnicianEntity> getTechnician(Application application, final String email) {
        return ((BaseApp) application).getDatabase().technicianDao().getByEmail(email);
    }
}
