package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.pojo.CompleteRevision;

/**
 * Revision repository singleton
 */
public class RevisionRepository {
    private static RevisionRepository instance;

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
     * Gets a revision by it's identifier
     * @param application Application
     * @param revisionId Revision's identifier
     * @return Revision with all it's associated objects
     */
    public LiveData<CompleteRevision> getRevision(Application application, int revisionId) {
        return ((BaseApp) application).getDatabase().revisionDao().getById(revisionId);
    }
}
