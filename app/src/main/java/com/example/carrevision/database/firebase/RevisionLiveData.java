package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.database.pojo.ModelWithBrand;
import com.example.carrevision.database.repository.BrandRepository;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.database.repository.ModelRepository;
import com.example.carrevision.database.repository.RevisionRepository;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Revision live data class
 */
public class RevisionLiveData extends BaseLiveData<CompleteRevision> {
    /**
     * Revision live data constructor
     * @param reference Database reference
     * @param revisionId Revision's identifier
     */
    public RevisionLiveData(DatabaseReference reference, String revisionId) {
        super("RevisionLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot revisionSnapshot = snapshot.child(RevisionRepository.TABLE).child(revisionId);
                CompleteRevision revision = new CompleteRevision();
                revision.revision = revisionSnapshot.getValue(RevisionEntity.class);
                if (revision.revision != null && revisionSnapshot.getKey() != null) {
                    revision.revision.setId(revisionSnapshot.getKey());
                    DataSnapshot carSnapshot = snapshot.child(CarRepository.TABLE).child(revision.revision.getCarId());
                    revision.completeCar = new CompleteCar();
                    revision.completeCar.car = carSnapshot.getValue(CarEntity.class);
                    if (revision.completeCar.car != null && carSnapshot.getKey() != null) {
                        revision.completeCar.car.setId(carSnapshot.getKey());
                        DataSnapshot modelSnapshot = snapshot.child(ModelRepository.TABLE).child(String.valueOf(revision.completeCar.car.getModelId()));
                        revision.completeCar.modelWithBrand = new ModelWithBrand();
                        revision.completeCar.modelWithBrand.model = modelSnapshot.getValue(ModelEntity.class);
                        if (revision.completeCar.modelWithBrand.model != null && modelSnapshot.getKey() != null) {
                            revision.completeCar.modelWithBrand.model.setId(Integer.parseInt(modelSnapshot.getKey()));
                            DataSnapshot brandSnapshot = snapshot.child(BrandRepository.TABLE).child(String.valueOf(revision.completeCar.modelWithBrand.model.getBrandId()));
                            revision.completeCar.modelWithBrand.brand = brandSnapshot.getValue(BrandEntity.class);
                            if (revision.completeCar.modelWithBrand.brand != null && brandSnapshot.getKey() != null) {
                                revision.completeCar.modelWithBrand.brand.setId(Integer.parseInt(brandSnapshot.getKey()));
                                DataSnapshot technicianSnapshot = snapshot.child(TechnicianRepository.TABLE).child(revision.revision.getTechnicianId());
                                revision.technician = technicianSnapshot.getValue(TechnicianEntity.class);
                                if (revision.technician != null && technicianSnapshot.getKey() != null) {
                                    revision.technician.setId(technicianSnapshot.getKey());
                                    setValue(revision);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
