package com.example.carrevision.viewmodel.technician;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.example.carrevision.viewmodel.BaseVM;

import java.util.List;

/**
 * Technician list view-model class
 */
public class TechnicianListVM extends BaseVM {
    private final TechnicianRepository repository;
    private final MediatorLiveData<List<TechnicianEntity>> observableTechnicians;

    /**
     * Technician list view-model class constructor
     * @param application Application
     * @param repository Technician repository
     */
    public TechnicianListVM(@NonNull Application application, TechnicianRepository repository) {
        super(application);
        this.repository = repository;
        observableTechnicians = new MediatorLiveData<>();
        observableTechnicians.setValue(null);

        LiveData<List<TechnicianEntity>> technicians = repository.getTechnicians(application);

        observableTechnicians.addSource(technicians, observableTechnicians::setValue);
    }

    /**
     * Inner class factory for the technician list view-model
     */
    public static class Factory extends BaseFactory {
        private final TechnicianRepository repository;

        /**
         * Inner class factory constructor
         * @param application Application
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.repository = getApp().getTechnicianRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TechnicianListVM(getApp(), repository);
        }
    }

    /**
     * Gets all the technicians from the database
     * @return List of all the technicians
     */
    public LiveData<List<TechnicianEntity>> getTechnicians() {
        return observableTechnicians;
    }
}
