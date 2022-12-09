package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.firebase.ModelListLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Model repository singleton
 */
public class ModelRepository {
    private static volatile ModelRepository instance;
    public static final String TABLE = "models";

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
     * @param brandId Brand's identifier
     * @return List with all models from the brand
     */
    public LiveData<List<ModelEntity>> getModelsByBrandId(int brandId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new ModelListLiveData(reference, brandId);
    }
}
