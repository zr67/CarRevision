package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.BrandEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Brand list live data class
 */
public class BrandListLiveData extends BaseLiveData<List<BrandEntity>> {
    /**
     * Brand list live data constructor
     * @param reference Database reference
     */
    public BrandListLiveData(DatabaseReference reference) {
        super("BrandListLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toBrands(snapshot));
            }
        });
    }

    /**
     * Converts a data snapshot to a list of brands
     * @param snapshot Data snapshot
     * @return List of brands
     */
    private List<BrandEntity> toBrands(DataSnapshot snapshot) {
        List<BrandEntity> brands = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            BrandEntity brand = childSnapshot.getValue(BrandEntity.class);
            if (brand != null && childSnapshot.getKey() != null) {
                brand.setId(Integer.parseInt(childSnapshot.getKey()));
                brands.add(brand);
            }
        }
        Collections.sort(brands);
        return brands;
    }
}
