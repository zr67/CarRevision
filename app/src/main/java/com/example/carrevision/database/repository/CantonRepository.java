package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.entity.CantonEntity;

import java.util.List;

/**
 * Canton repository singleton
 */
public class CantonRepository {
    private static CantonRepository instance;

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
     * @param application Application
     * @return List with all the cantons
     */
    public LiveData<List<CantonEntity>> getCantons(Application application) {
        return ((BaseApp) application).getDatabase().cantonDao().getAll();
    }
}
