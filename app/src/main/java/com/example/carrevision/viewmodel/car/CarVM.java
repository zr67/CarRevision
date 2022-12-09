package com.example.carrevision.viewmodel.car;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.viewmodel.BaseVM;

/**
 * Car view-model class
 */
public class CarVM extends BaseVM {
    private final CarRepository repository;
    private final MediatorLiveData<CompleteCar> observableCar;

    /**
     * Car view-model class constructor
     * @param application Application
     * @param repository Car repository
     * @param carId Car identifier
     */
    public CarVM(@NonNull Application application, CarRepository repository, String carId) {
        super(application);
        this.repository = repository;
        observableCar = new MediatorLiveData<>();
        observableCar.setValue(null);

        if (carId != null) {
            if (!TextUtils.isEmpty(carId)) {
                LiveData<CompleteCar> car = repository.getCar(carId);
                observableCar.addSource(car, observableCar::setValue);
            }
        }
    }

    /**
     * Inner class factory for the car view-model
     */
    public static class Factory extends BaseFactory {
        private final CarRepository repository;
        private final String carId;

        /**
         * Inner class factory constructor
         * @param application Application
         * @param carId Car identifier
         */
        public Factory(@NonNull Application application, String carId) {
            super(application);
            this.repository = getApp().getCarRepository();
            this.carId = carId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarVM(getApp(), repository, carId);
        }
    }

    /**
     * Gets the observable car
     * @return Observable car
     */
    public LiveData<CompleteCar> getCar() { return observableCar; }

    /**
     * Creates a car
     * @param car Car to create
     * @param callback Callback
     */
    public void createCar(CarEntity car, OnAsyncEventListener callback) {
        repository.create(car, callback);
    }

    /**
     * Updates a car
     * @param car Car to update
     * @param callback Callback
     */
    public void updateCar(CarEntity car, OnAsyncEventListener callback) {
        repository.update(car, callback);
    }

    /**
     * Deletes a car
     * @param car Car to delete
     * @param callback Callback
     */
    public void deleteCar(CarEntity car, OnAsyncEventListener callback) {
        repository.delete(car, callback);
    }
}
