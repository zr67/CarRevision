package com.example.carrevision.viewmodel.technician;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.example.carrevision.viewmodel.BaseVM;

/**
 * Technician view-model class
 */
public class TechnicianVM extends BaseVM {
    private final MediatorLiveData<TechnicianEntity> observableTechnician;

    /**
     * Revision view-model class constructor
     * @param application Application
     * @param repository Technician repository
     * @param technicianId Technician identifier
     */
    public TechnicianVM(@NonNull Application application, TechnicianRepository repository, int technicianId) {
        super(application);
        observableTechnician = new MediatorLiveData<>();
        observableTechnician.setValue(null);

        LiveData<TechnicianEntity> technician = repository.getTechnician(application, technicianId);

        observableTechnician.addSource(technician, observableTechnician::setValue);
    }

    /**
     * Inner class factory for the technician view-model
     */
    public static class Factory extends BaseFactory {
        private final TechnicianRepository repository;
        private final int technicianId;

        /**
         * Inner class factory constructor
         * @param application Application
         * @param technicianId Technician identifier
         */
        public Factory(@NonNull Application application, int technicianId) {
            super(application);
            this.repository = getApp().getTechnicianRepository();
            this.technicianId = technicianId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TechnicianVM(getApp(), repository, technicianId);
        }
    }

    /**
     * Gets the observable technician
     * @return Observable technician
     */
    public LiveData<TechnicianEntity> getTechnician() {
        return observableTechnician;
    }
}
