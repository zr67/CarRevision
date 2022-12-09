package com.example.carrevision;

import android.app.Application;

import com.example.carrevision.database.repository.BrandRepository;
import com.example.carrevision.database.repository.CantonRepository;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.database.repository.ModelRepository;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.database.repository.TechnicianRepository;

/**
 * Base application class
 */
public class BaseApp extends Application {

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

    /**
     * Gets the brand repository
     * @return Brand repository
     */
    public BrandRepository getBrandRepository() {
        return BrandRepository.getInstance();
    }

    /**
     * Gets the model repository
     * @return Model repository
     */
    public ModelRepository getModelRepository() {
        return ModelRepository.getInstance();
    }
}
