package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Technician list live data class
 */
public class TechnicianListLiveData extends BaseLiveData<List<TechnicianEntity>> {

    /**
     * Technician list live data constructor
     * @param reference Database reference
     */
    public TechnicianListLiveData(DatabaseReference reference) {
        super("TechnicianListLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toTechnicians(snapshot));
            }
        });
    }

    /**
     * Converts a data snapshot to a list of technicians
     * @param snapshot Data snapshot
     * @return List of technicians
     */
    private List<TechnicianEntity> toTechnicians(DataSnapshot snapshot) {
        List<TechnicianEntity> technicians = new ArrayList<>();
        for (DataSnapshot technicianSnapshot : snapshot.getChildren()) {
            if (technicianSnapshot.getKey() != null) {
                TechnicianEntity technician = technicianSnapshot.getValue(TechnicianEntity.class);
                if (technician != null) {
                    technician.setId(technicianSnapshot.getKey());
                    technicians.add(technician);
                }
            }
        }
        Collections.sort(technicians);
        return technicians;
    }
}
