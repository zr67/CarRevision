package com.example.carrevision.viewmodel.car;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Car list view-model class
 */
public class CarListVM extends BaseVM {
    private final MediatorLiveData<List<CompleteCar>> observableCars;

    /**
     * Car list view-model constructor
     * @param application Application
     * @param repository Car repository
     */
    public CarListVM(@NonNull Application application, CarRepository repository) {
        super(application);
        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<CompleteCar>> cars = repository.getCars();

        observableCars.addSource(cars, observableCars::setValue);
    }

    /**
     * Inner class factory for the car list view-model
     */
    public static class Factory extends BaseFactory {
        private final CarRepository repository;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getCarRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListVM(getApp(), repository);
        }
    }

    /**
     * Gets all the observable cars
     * @return Observable cars
     */
    public LiveData<List<CompleteCar>> getCars() {
        return observableCars;
    }
}
