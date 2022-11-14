package com.example.carrevision.viewmodel.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.repository.ModelRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Model list view-model class
 */
public class ModelListVM extends BaseVM {
    private final MediatorLiveData<List<ModelEntity>> observableModels;

    /**
     * Model list view-model class constructor
     * @param application Application
     * @param repository Model repository
     * @param brandId Brand identifier
     */
    public ModelListVM(@NonNull Application application, ModelRepository repository, int brandId) {
        super(application);
        observableModels = new MediatorLiveData<>();
        observableModels.setValue(null);

        LiveData<List<ModelEntity>> models = repository.getModelsByBrandId(application, brandId);

        observableModels.addSource(models, observableModels::setValue);
    }

    /**
     * Inner class factory for the model list view-model
     */
    public static class Factory extends BaseFactory {
        private final ModelRepository repository;
        private final int brandId;

        /**
         * Inner class factory constructor
         * @param application Application
         * @param brandId Brand identifier
         */
        public Factory(@NonNull Application application, int brandId) {
            super(application);
            this.repository = getApp().getModelRepository();
            this.brandId = brandId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ModelListVM(getApp(), repository, brandId);
        }
    }

    /**
     * Gets all models by brand id from the database
     * @return Observable models
     */
    public LiveData<List<ModelEntity>> getModelsByBrandId() {
        return observableModels;
    }
}
