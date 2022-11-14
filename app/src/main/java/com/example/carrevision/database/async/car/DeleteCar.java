package com.example.carrevision.database.async.car;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.BaseAsync;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Async class for car deletion
 */
public class DeleteCar extends BaseAsync<CarEntity> {
    /**
     * Car deletion class constructor
     * @param application Application to access the dao interfaces
     * @param callback Callback interface to notify the operation status
     */
    public DeleteCar(BaseApp application, OnAsyncEventListener callback) {
        super(application, callback);
    }
    @Override
    protected Void doInBackground(CarEntity... carEntities) {
        try {
            for (CarEntity ce : carEntities) {
                application.getDatabase().carDao().deleteById(ce.getId());
            }
        }
        catch (Exception e) {
            exception = e;
        }
        return null;
    }
}

