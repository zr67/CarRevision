package com.example.carrevision.database.async.car;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.async.BaseAsync;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Async class for car update
 */
public class UpdateCar extends BaseAsync<CarEntity> {

    public UpdateCar(BaseApp application, OnAsyncEventListener callback) {
        super(application, callback);
    }

    @Override
    protected Void doInBackground(CarEntity... carEntities) {
        try {
            for (CarEntity ce : carEntities) {
                application.getDatabase().carDao().update(ce);
            }
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
}