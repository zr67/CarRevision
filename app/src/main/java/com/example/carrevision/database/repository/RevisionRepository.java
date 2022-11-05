package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.revision.CreateRevision;
import com.example.carrevision.database.async.revision.DeleteRevision;
import com.example.carrevision.database.async.revision.UpdateRevision;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.util.OnAsyncEventListener;

import java.util.List;

/**
 * Revision repository singleton
 */
public class RevisionRepository {
    private static volatile RevisionRepository instance;

    /**
     * Revision repository private constructor
     */
    private RevisionRepository() {}

    /**
     * Gets the revision repository instance
     * @return Revision repository instance
     */
    public static RevisionRepository getInstance() {
        if (instance == null) {
            synchronized (RevisionRepository.class) {
                if (instance == null) {
                    instance = new RevisionRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all revisions from the database
     * @param application Application
     * @return List with all the revisions
     */
    public LiveData<List<CompleteRevision>> getRevisions(Application application) {
        return ((BaseApp) application).getDatabase().revisionDao().getAll();
    }

    /**
     * Gets a revision by it's identifier
     * @param application Application
     * @param revisionId Revision's identifier
     * @return Revision with all it's associated objects
     */
    public LiveData<CompleteRevision> getRevision(Application application, int revisionId) {
        return ((BaseApp) application).getDatabase().revisionDao().getById(revisionId);
    }

    /**
     * Creates a new revision
     * @param revision Revision to create
     * @param callback Callback
     * @param application Application
     */
    public void create(final RevisionEntity revision, OnAsyncEventListener callback, Application application) {
        new CreateRevision((BaseApp) application, callback).execute(revision);
    }

    /**
     * Updates a revision
     * @param revision Revision to update
     * @param callback Callback
     * @param application Application
     */
    public void update(final RevisionEntity revision, OnAsyncEventListener callback, Application application) {
        new UpdateRevision((BaseApp) application, callback).execute(revision);
    }

    /**
     * Deletes a revision
     * @param revision Revision to delete
     * @param callback Callback
     * @param application Application
     */
    public void delete(final RevisionEntity revision, OnAsyncEventListener callback, Application application) {
        new DeleteRevision((BaseApp) application, callback).execute(revision);
    }
}
