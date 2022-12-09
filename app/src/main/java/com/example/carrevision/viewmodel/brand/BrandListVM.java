package com.example.carrevision.viewmodel.brand;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.repository.BrandRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Brand list view-model class
 */
public class BrandListVM extends BaseVM {
    private final MediatorLiveData<List<BrandEntity>> observableBrands;

    /**
     * Brand list view-model constructor
     * @param application Application
     * @param repository Brand repository
     */
    public BrandListVM(@NonNull Application application, BrandRepository repository) {
        super(application);
        observableBrands = new MediatorLiveData<>();
        observableBrands.setValue(null);

        LiveData<List<BrandEntity>> brands = repository.getBrands();

        observableBrands.addSource(brands, observableBrands::setValue);
    }

    /**
     * Inner class factory for the brand list view-model
     */
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
            return (T) new BrandListVM(getApp(), repository);
        }
    }

    /**
     * Gets all the observable brands
     * @return Observable brands
     */
    public LiveData<List<BrandEntity>> getBrands() {
        return observableBrands;
    }
}
