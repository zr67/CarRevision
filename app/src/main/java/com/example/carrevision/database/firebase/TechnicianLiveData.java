package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Technician live data class
 */
public class TechnicianLiveData extends BaseLiveData<TechnicianEntity> {

    /**
     * Technician live data constructor
     * @param reference Database reference
     * @param id Technician's uid
     */
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
