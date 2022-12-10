package com.example.carrevision.viewmodel.revision;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Revision light list view-model class
 */
public class RevisionLightListVM extends BaseVM {
    private final MediatorLiveData<List<RevisionEntity>> observableRevisions;

    /**
     * Revision list view-model constructor
     * @param application Application
     * @param repository Revision repository
     * @param carId Filter revisions by linked car id
     */
    public RevisionLightListVM(@NonNull Application application, RevisionRepository repository, String carId) {
        super(application);
        observableRevisions = new MediatorLiveData<>();
        observableRevisions.setValue(null);

        LiveData<List<RevisionEntity>> revisions = repository.getRevisionsLight(carId);

        observableRevisions.addSource(revisions, observableRevisions::setValue);
    }

    /**
     * Inner class factory for the car list view-model
     */
    public static class Factory extends BaseFactory {
        private final RevisionRepository repository;
        private final String carId;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application, String carId) {
            super(application);
            this.repository = getApp().getRevisionRepository();
            this.carId = carId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RevisionLightListVM(getApp(), repository, carId);
        }
    }

    /**
     * Gets all the observable cars
     * @return Observable cars
     */
    public LiveData<List<RevisionEntity>> getRevisions() {
        return observableRevisions;
    }
}

