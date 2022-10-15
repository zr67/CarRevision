package com.example.carrevision.util;

/**
 * Interface used to notify an async task result
 */
public interface OnAsyncEventListener {
    /**
     * Method to be called if the task was completed successfully
     */
    void onSuccess();

    /**
     * Method to be called if the task failed
     * @param e Task's exception
     */
    void onFailure(Exception e);
}
