package com.example.carrevision.database.firebase;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.pojo.ModelWithBrand;
import com.example.carrevision.database.repository.BrandRepository;
import com.example.carrevision.database.repository.CarRepository;
import com.example.carrevision.database.repository.ModelRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Car live data class
 */
public class CarLiveData extends BaseLiveData<CompleteCar> {
    /**
     * Car live data constructor
     * @param reference Database reference
     * @param carId Car's identifier
     */
    public CarLiveData(DatabaseReference reference, String carId) {
        super("CarLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot carSnapshot = snapshot.child(CarRepository.TABLE).child(carId);
                CompleteCar car = new CompleteCar();
                car.car = carSnapshot.getValue(CarEntity.class);
                if (car.car != null && carSnapshot.getKey() != null) {
                    car.car.setId(carId);
                    DataSnapshot modelSnapshot = snapshot.child(ModelRepository.TABLE).child(String.valueOf(car.car.getModelId()));
                    car.modelWithBrand = new ModelWithBrand();
                    car.modelWithBrand.model = modelSnapshot.getValue(ModelEntity.class);
                    if (car.modelWithBrand.model != null && modelSnapshot.getKey() != null) {
                        car.modelWithBrand.model.setId(Integer.parseInt(modelSnapshot.getKey()));
                        DataSnapshot brandSnapshot = snapshot.child(BrandRepository.TABLE).child(String.valueOf(car.modelWithBrand.model.getBrandId()));
                        car.modelWithBrand.brand = brandSnapshot.getValue(BrandEntity.class);
                        if (car.modelWithBrand.brand != null && brandSnapshot.getKey() != null) {
                            car.modelWithBrand.brand.setId(Integer.parseInt(brandSnapshot.getKey()));
                            setValue(car);
                        }
                    }
                }
            }
        });
    }
}
