package com.example.carrevision.viewmodel.canton;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.pojo.BrandWithModels;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.repository.BrandRepository;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.viewmodel.BaseVM;
import com.example.carrevision.viewmodel.car.CarListVM;

import java.util.List;

public class BrandModelListVM extends BaseVM {

    private final MediatorLiveData<List<BrandWithModels>> observableBrandWithModels;

    public BrandModelListVM(@NonNull Application application, BrandRepository repository) {
        super(application);
        observableBrandWithModels = new MediatorLiveData<>();
        observableBrandWithModels.setValue(null);

        LiveData<List<BrandWithModels>> brandWithModels = repository.getBrandWithModels(application);

        observableBrandWithModels.addSource(brandWithModels, observableBrandWithModels::setValue);
    }
    public static class Factory extends BaseFactory {
        private final BrandRepository repository;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getBrandRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BrandModelListVM(getApp(), repository);
        }
    }

    public LiveData<List<BrandWithModels>> getBrandWithModels() {
        return observableBrandWithModels;
    }
}