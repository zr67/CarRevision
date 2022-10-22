package com.example.carrevision.database.async.revision;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.BaseAsync;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Async class for revision update
 */
public class UpdateRevision extends BaseAsync<RevisionEntity> {
    /**
     * Revision update class constructor
     * @param application Application to access the dao interfaces
     * @param callback Callback interface to notify the operation status
     */
    public UpdateRevision(BaseApp application, OnAsyncEventListener callback) {
        super(application, callback);
    }
    @Override
    protected Void doInBackground(RevisionEntity... revisionEntities) {
        try {
            for (RevisionEntity re : revisionEntities) {
                application.getDatabase().revisionDao().update(re);
            }
        }
        catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
