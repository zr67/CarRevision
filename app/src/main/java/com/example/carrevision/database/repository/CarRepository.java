package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.car.CreateCar;
import com.example.carrevision.database.async.car.DeleteCar;
import com.example.carrevision.database.async.car.UpdateCar;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.util.OnAsyncEventListener;

import java.util.List;

/**
 * Car repository singleton
 */
public class CarRepository {
    private static volatile CarRepository instance;

    /**
     * Car repository class private constructor
     */
    private CarRepository() {}

    /**
     * Gets the car repository instance
     * @return Car repository instance
     */
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

    /**
     * Gets all cars with their model and brand from the database
     * @param application Application
     * @return List with all the cars
     */
    public LiveData<List<CompleteCar>> getCars(Application application) {
        return ((BaseApp) application).getDatabase().carDao().getAll();
    }

    public LiveData<CompleteCar> getCar(Application application, int carId) {
        return ((BaseApp) application).getDatabase().carDao().getById(carId);
    }

    public void update(final CarEntity car, OnAsyncEventListener callback, Application application) {
        new UpdateCar((BaseApp) application, callback).execute(car);
    }

    public void create(final CarEntity car, OnAsyncEventListener callback, Application application) {
        new CreateCar((BaseApp) application, callback).execute(car);
    }

    public void delete(final CarEntity car, OnAsyncEventListener callback, Application application) {
        new DeleteCar((BaseApp) application, callback).execute(car);
    }
}
