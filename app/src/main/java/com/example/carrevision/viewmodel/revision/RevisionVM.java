package com.example.carrevision.viewmodel.revision;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.viewmodel.BaseVM;

/**
 * Revision view-model class
 */
public class RevisionVM extends BaseVM {
    private final RevisionRepository repository;
    private final MediatorLiveData<CompleteRevision> observableRevision;

    /**
     * Revision view-model class constructor
     * @param application Application
     * @param repository Revision repository
     * @param revisionId Revision identifier
     */
    public RevisionVM(@NonNull Application application, RevisionRepository repository, int revisionId) {
        super(application);
        this.repository = repository;
        observableRevision = new MediatorLiveData<>();
        observableRevision.setValue(null);

        LiveData<CompleteRevision> revision = repository.getRevision(application, revisionId);

        observableRevision.addSource(revision, observableRevision::setValue);
    }

    /**
     * Inner class factory for the revision view-model
     */
    public static class Factory extends BaseFactory {
        private final RevisionRepository repository;
        private final int revisionId;

        /**
         * Inner class factory constructor
         * @param application Application
         * @param revisionId Revision identifier
         */
        public Factory(@NonNull Application application, int revisionId) {
            super(application);
            this.repository = getApp().getRevisionRepository();
            this.revisionId = revisionId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RevisionVM(getApp(), repository, revisionId);
        }
    }

    /**
     * Gets the observable revision
     * @return Observable revision
     */
    public LiveData<CompleteRevision> getRevision() {
        return observableRevision;
    }

    /**
     * Updates a revision
     * @param revision Revision to update
     * @param callback Callback
     */
    public void updateRevision(RevisionEntity revision, OnAsyncEventListener callback) {
        repository.update(revision, callback, application);
    }
}
