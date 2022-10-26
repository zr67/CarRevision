package com.example.carrevision.viewmodel.canton;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.repository.CantonRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Canton list view-model class
 */
public class CantonListVM extends BaseVM {
    private final MediatorLiveData<List<CantonEntity>> observableCantons;

    /**
     * Canton list view-model class constructor
     * @param application Application
     * @param repository Canton repository
     */
    public CantonListVM(@NonNull Application application, CantonRepository repository) {
        super(application);
        observableCantons = new MediatorLiveData<>();
        observableCantons.setValue(null);

        LiveData<List<CantonEntity>> cantons = repository.getCantons(application);

        observableCantons.addSource(cantons, observableCantons::setValue);
    }

    /**
     * Inner class factory for the canton list view-model
     */
    public static class Factory extends BaseFactory {
        private final CantonRepository repository;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getCantonRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CantonListVM(getApp(), repository);
        }
    }

    /**
     * Gets all the observable cantons
     * @return Observable cantons
     */
    public LiveData<List<CantonEntity>> getCantons() {
        return observableCantons;
    }
}
