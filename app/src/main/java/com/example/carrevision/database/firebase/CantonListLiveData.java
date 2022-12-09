package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.CantonEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Canton list live data class
 */
public class CantonListLiveData extends BaseLiveData<List<CantonEntity>> {

    /**
     * Canton list live data constructor
     * @param reference Database reference
     */
    public CantonListLiveData(DatabaseReference reference) {
        super("CantonListLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toCantons(snapshot));
            }
        });
    }

    /**
     * Converts a data snapshot to a list of cantons
     * @param snapshot Data snapshot
     * @return List of cantons
     */
    private List<CantonEntity> toCantons(DataSnapshot snapshot) {
        List<CantonEntity> cantons = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CantonEntity canton = childSnapshot.getValue(CantonEntity.class);
            if (canton != null && childSnapshot.getKey() != null) {
                canton.setId(Integer.parseInt(childSnapshot.getKey()));
                cantons.add(canton);
            }
        }
        Collections.sort(cantons);
        return cantons;
    }
}
