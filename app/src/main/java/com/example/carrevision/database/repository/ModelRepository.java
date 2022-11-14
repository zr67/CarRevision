package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.entity.ModelEntity;

import java.util.List;

/**
 * Model repository singleton
 */
public class ModelRepository {
    private static volatile ModelRepository instance;

    /**
     * Model repository class private constructor
     */
    private ModelRepository() {}

    /**
     * Gets the model repository instance
     * @return Model repository instance
     */
    public static ModelRepository getInstance() {
        if (instance == null) {
            synchronized (ModelRepository.class) {
                if (instance == null) {
                    instance = new ModelRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all models by brand identifier from the database
     * @param application Application
     * @param brandId Brand's identifier
     * @return List with all models from the brand
     */
    public LiveData<List<ModelEntity>> getModelsByBrandId(Application application, int brandId) {
        return ((BaseApp) application).getDatabase().modelDao().getByBrandId(brandId);
    }
}
