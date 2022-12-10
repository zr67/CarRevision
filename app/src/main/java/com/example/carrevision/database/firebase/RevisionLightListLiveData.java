package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.RevisionEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Revision light list live data class
 */
public class RevisionLightListLiveData extends BaseLiveData<List<RevisionEntity>> {
    /**
     * Revision light list live data constructor
     * @param reference Database reference
     * @param carId Filter revision list by linked car id
     */
    public RevisionLightListLiveData(DatabaseReference reference, String carId) {
        super("RevisionLightListLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toRevisions(snapshot, carId));
            }
        });
    }

    /**
     * Converts the data snapshot to a revision list
     * @param snapshot Data snapshot
     * @param carId Car's id to filter the revisions
     * @return List of revisions
     */
    private List<RevisionEntity> toRevisions(DataSnapshot snapshot, String carId) {
        List<RevisionEntity> revisions = new ArrayList<>();
        for (DataSnapshot revisionSnapshot : snapshot.getChildren()) {
            if (revisionSnapshot.getKey() != null) {
                RevisionEntity revision = revisionSnapshot.getValue(RevisionEntity.class);
                if (revision != null) {
                    revision.setId(revisionSnapshot.getKey());
                    if (revision.getCarId().equals(carId)) {
                        revisions.add(revision);
                    }
                }
            }
        }
        return revisions;
    }
}
