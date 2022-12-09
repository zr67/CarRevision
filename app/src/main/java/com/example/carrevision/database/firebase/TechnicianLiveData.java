package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Technician live data class
 */
public class TechnicianLiveData extends BaseLiveData<TechnicianEntity> {

    /**
     * Technician live data constructor
     * @param reference Database reference
     */
    public TechnicianLiveData(DatabaseReference reference) {
        super("TechnicianLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TechnicianEntity technician = snapshot.getValue(TechnicianEntity.class);
                if (technician != null && snapshot.getKey() != null) {
                    technician.setId(snapshot.getKey());
                    setValue(technician);
                }
            }
        });
    }

    public TechnicianLiveData(DatabaseReference reference, String id) {
        super("TechnicianLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot technicianSnapshot = snapshot.child(id);
                if (technicianSnapshot.getKey() != null) {
                    TechnicianEntity technician = technicianSnapshot.getValue(TechnicianEntity.class);
                    if (technician != null) {
                        setValue(technician);
                    }
                }
            }
        });
    }
}
