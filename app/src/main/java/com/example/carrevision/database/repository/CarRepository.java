package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.entity.CarEntity;

import java.util.List;

public class CarRepository {
    private static CarRepository instance;

    private CarRepository() {}
    public static CarRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<CarEntity>> getCars(Application application) {
        return ((BaseApp) application).getDatabase().carDao().getAll();
    }
}
