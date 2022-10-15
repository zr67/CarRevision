package com.example.carrevision.database.async;

import android.os.AsyncTask;

import com.example.carrevision.BaseApp;
import com.example.carrevision.util.OnAsyncEventListener;

/**
 * Base class for async database operations.
 * @param <T> Entity
 */
public abstract class BaseAsync<T> extends AsyncTask<T, Void, Void> {
    protected final BaseApp application;
    protected final OnAsyncEventListener callback;
    protected Exception exception;

    /**
     * Base class constructor
     * @param application Application to access the dao interfaces
     * @param callback Callback interface to notify the operation status
     */
    public BaseAsync(BaseApp application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }
    
    @Override
    protected void onPostExecute(Void unused) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            }
            else {
                callback.onFailure(exception);
            }
        }
    }
}
