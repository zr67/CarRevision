package com.example.carrevision.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carrevision.BaseApp;

public abstract class BaseVM extends AndroidViewModel {
    protected final Application application;

    public BaseVM(Application application) {
        super(application);
        this.application = application;
    }

    public static abstract class BaseFactory implements ViewModelProvider.Factory {
        private final Application application;

        public BaseFactory(Application application) {
            this.application = application;
        }

        protected BaseApp getApp() {
            return (BaseApp) application;
        }

        @NonNull
        @Override
        public abstract <T extends ViewModel> T create(@NonNull Class<T> modelClass);
    }
}
