package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.ModelEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model list live data class
 */
public class ModelListLiveData extends BaseLiveData<List<ModelEntity>> {
    private final int brandId;

    /**
     * Model list live data constructor
     * @param reference Database reference
     * @param brandId Brand's identifier
     */
    public ModelListLiveData(DatabaseReference reference, final int brandId) {
        super("ModelListLiveData", reference);
        this.brandId = brandId;
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toModels(snapshot));
            }
        });
    }

    /**
     * Converts a data snapshot to a list of models
     * @param snapshot Data snapshot
     * @return List of models
     */
    private List<ModelEntity> toModels(DataSnapshot snapshot) {
        List<ModelEntity> models = new ArrayList<>();
        for (DataSnapshot modelSnapshot : snapshot.getChildren()) {
            DataSnapshot brandIdSnapshot = modelSnapshot.child("brandId");
            if (brandIdSnapshot.getValue() != null) {
                if ((Long) brandIdSnapshot.getValue() == Integer.toUnsignedLong(brandId)) {
                    ModelEntity model = modelSnapshot.getValue(ModelEntity.class);
                    if (model != null && modelSnapshot.getKey() != null) {
                        model.setId(Integer.parseInt(modelSnapshot.getKey()));
                        models.add(model);
                    }
                }
            }
        }
        Collections.sort(models);
        return models;
    }
}
