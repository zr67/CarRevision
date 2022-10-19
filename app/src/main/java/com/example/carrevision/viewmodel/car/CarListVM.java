package com.example.carrevision.viewmodel.car;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Car list view-model class
 */
public class CarListVM extends BaseVM {
    private final CarRepository repository;
    private final MediatorLiveData<List<CarEntity>> observableCars;

    public CarListVM(@NonNull Application application, CarRepository repository) {
        super(application);
        this.repository = repository;
        observableCars = new MediatorLiveData<>();
        observableCars.setValue(null);

        LiveData<List<CarEntity>> cars = repository.getCars(application);

        observableCars.addSource(cars, observableCars::setValue);
    }

    public static class Factory extends BaseFactory {
        private final CarRepository repository;

        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getCarRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CarListVM(getApp(), repository);
        }
    }

    public LiveData<List<CarEntity>> getCars() {
        return observableCars;
    }
}
