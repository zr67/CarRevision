package com.example.carrevision.database.async.revision;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.BaseAsync;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Async class for revision deletion
 */
public class DeleteRevision extends BaseAsync<RevisionEntity> {
    /**
     * Revision deletion class constructor
     * @param application Application to access the dao interfaces
     * @param callback Callback interface to notify the operation status
     */
    public DeleteRevision(BaseApp application, OnAsyncEventListener callback) {
        super(application, callback);
    }
    @Override
    protected Void doInBackground(RevisionEntity... revisionEntities) {
        try {
            for (RevisionEntity re : revisionEntities) {
                application.getDatabase().revisionDao().deleteById(re.getId());
            }
        }
        catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
