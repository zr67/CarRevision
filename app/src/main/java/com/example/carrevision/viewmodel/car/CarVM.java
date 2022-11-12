package com.example.carrevision.viewmodel.car;

import android.app.Application;

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

    public CarVM(@NonNull Application application, CarRepository repository, int carId) {
        super(application);
        this.repository = repository;
        observableCar = new MediatorLiveData<>();
        observableCar.setValue(null);

        LiveData<CompleteCar> car = repository.getCar(application, carId);

        observableCar.addSource(car, observableCar::setValue);
    }

    public LiveData<CompleteCar> getCar() { return observableCar; }

    public void updateCar(CarEntity car, OnAsyncEventListener callback) {
        repository.update(car, callback, application);
    }

    public void createCar(CarEntity car, OnAsyncEventListener callback) {
        repository.create(car, callback, application);
    }

    public void deleteCar(CarEntity car, OnAsyncEventListener callback) {
        repository.delete(car, callback, application);
    }

    public static class Factory extends BaseFactory {
        private final CarRepository repository;
        private final int carId;

        public Factory(@NonNull Application application, int carId) {
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
}
