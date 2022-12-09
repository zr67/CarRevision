package com.example.carrevision.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Base class for the live data classes
 * @param <T> Live data type
 */
public abstract class BaseLiveData<T> extends LiveData<T> {
    protected final String TAG;
    protected final DatabaseReference reference;
    protected MyValueEventListener listener;

    /**
     * Base live data class constructor
     * @param TAG Live data class tag
     * @param reference Database reference
     */
    public BaseLiveData(String TAG, DatabaseReference reference) {
        this.TAG = TAG;
        this.reference = reference;
    }

    /**
     * Sets the value listener
     * @param listener Value listener
     */
    protected void setListener(MyValueEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }
    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    /**
     * Base value listener class
     */
    protected abstract class MyValueEventListener implements ValueEventListener {
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, "Can't listen to query " + reference, error.toException());
        }
    }
}
