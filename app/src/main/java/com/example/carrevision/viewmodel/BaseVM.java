package com.example.carrevision.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carrevision.BaseApp;

/**
 * Base view-model class
 */
public abstract class BaseVM extends AndroidViewModel {
    protected final Application application;

    /**
     * Base view-model class constructor
     * @param application Application
     */
    public BaseVM(Application application) {
        super(application);
        this.application = application;
    }

    /**
     * Base inner class for the view-model factory
     */
    public static abstract class BaseFactory implements ViewModelProvider.Factory {
        private final Application application;

        /**
         * Base inner class for the view-model factory constructor
         * @param application Application
         */
        public BaseFactory(Application application) {
            this.application = application;
        }

        /**
         * Gets the application
         * @return Application
         */
        protected BaseApp getApp() {
            return (BaseApp) application;
        }

        @NonNull
        @Override
        public abstract <T extends ViewModel> T create(@NonNull Class<T> modelClass);
    }
}
