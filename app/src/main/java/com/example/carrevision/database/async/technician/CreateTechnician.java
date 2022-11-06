package com.example.carrevision.database.async.technician;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.BaseAsync;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Async class for technician creation
 */
public class CreateTechnician extends BaseAsync<TechnicianEntity> {
    /**
     * Technician creation class constructor
     * @param application Application to access the dao interfaces
     * @param callback Callback interface to notify the operation status
     */
    public CreateTechnician(BaseApp application, OnAsyncEventListener callback) {
        super(application, callback);
    }
    @Override
    protected Void doInBackground(TechnicianEntity... technicianEntities) {
        try {
            for (TechnicianEntity te : technicianEntities) {
                application.getDatabase().technicianDao().insert(te);
            }
        }
        catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
