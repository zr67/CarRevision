package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.entity.BrandEntity;

import java.util.List;

/**
 * Brand repository singleton
 */
public class BrandRepository {
    private static volatile BrandRepository instance;

    /**
     * Brand repository class private constructor
     */
    private BrandRepository() {}

    /**
     * Gets the brand repository instance
     * @return Brand repository instance
     */
    public static BrandRepository getInstance(){
        if (instance == null) {
            synchronized (BrandRepository.class) {
                if (instance == null) {
                    instance = new BrandRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all brands from the database
     * @param application Application
     * @return List with all the brands
     */
    public LiveData<List<BrandEntity>> getBrands(Application application) {
        return ((BaseApp) application).getDatabase().brandDao().getAll();
    }
}


