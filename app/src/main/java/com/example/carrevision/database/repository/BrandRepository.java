package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.firebase.BrandListLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Brand repository singleton
 */
public class BrandRepository {
    private static volatile BrandRepository instance;
    public static final String TABLE = "brands";

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
     * @return List with all the brands
     */
    public LiveData<List<BrandEntity>> getBrands() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new BrandListLiveData(reference);
    }
}


