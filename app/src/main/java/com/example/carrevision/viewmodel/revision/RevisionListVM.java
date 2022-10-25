package com.example.carrevision.viewmodel.revision;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.viewmodel.BaseVM;
import com.example.carrevision.viewmodel.car.CarListVM;

import java.util.List;

/**
 * Revision list view-model class
 */
public class RevisionListVM extends BaseVM {
    private final RevisionRepository repository;
    private final MediatorLiveData<List<CompleteRevision>> observableRevisions;

    /**
     * Revision list view-model constructor
     * @param application Application
     * @param repository Revision repository
     */
    public RevisionListVM(@NonNull Application application, RevisionRepository repository) {
        super(application);
        this.repository = repository;
        observableRevisions = new MediatorLiveData<>();
        observableRevisions.setValue(null);

        LiveData<List<CompleteRevision>> revisions = repository.getRevisions(application);

        observableRevisions.addSource(revisions, observableRevisions::setValue);
    }

    /**
     * Inner class factory for the car list view-model
     */
    public static class Factory extends BaseFactory {
        private final RevisionRepository repository;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getRevisionRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new RevisionListVM(getApp(), repository);
        }
    }

    /**
     * Gets all the observable cars
     * @return Observable cars
     */
    public LiveData<List<CompleteRevision>> getRevisions() {
        return observableRevisions;
    }
}

