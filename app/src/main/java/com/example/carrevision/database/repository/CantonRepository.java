package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.firebase.CantonListLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Canton repository singleton
 */
public class CantonRepository {
    private static volatile CantonRepository instance;
    public static final String TABLE = "cantons";

    /**
     * Canton repository class private constructor
     */
    private CantonRepository() {}

    /**
     * Gets the canton repository instance
     * @return Canton repository instance
     */
    public static CantonRepository getInstance() {
        if (instance == null) {
            synchronized (CantonRepository.class) {
                if (instance == null) {
                    instance = new CantonRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all cantons from the database
     * @return List with all the cantons
     */
    public LiveData<List<CantonEntity>> getCantons() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new CantonListLiveData(reference);
    }
}
