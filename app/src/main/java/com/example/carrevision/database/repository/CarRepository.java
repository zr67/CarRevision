package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.firebase.CarListLiveData;
import com.example.carrevision.database.firebase.CarLiveData;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Car repository singleton
 */
public class CarRepository {
    private static volatile CarRepository instance;
    public static final String TABLE = "cars";

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
     * @return List with all the cars
     */
    public LiveData<List<CompleteCar>> getCars() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new CarListLiveData(reference);
    }

    /**
     * Gets a car by it's identifier
     * @param carId Car's identifier
     * @return Car with all it's associated objects
     */
    public LiveData<CompleteCar> getCar(String carId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new CarLiveData(reference, carId);
    }

    /**
     * Creates a new car
     * @param car Car to be created
     * @param callback Callback
     */
    public void create(final CarEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .push()
                .setValue(car, (dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * Updates a car
     * @param car Car to update
     * @param callback Callback
     */
    public void update(final CarEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .child(car.getId())
                .updateChildren(car.toMap(), (dbError, dbRef) -> {
                    if (dbError != null) {
                        callback.onFailure(dbError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * Deletes a car
     * @param car Car to delete
     * @param callback Callback
     */
    public void delete(final CarEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .child(car.getId())
                .removeValue((dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
