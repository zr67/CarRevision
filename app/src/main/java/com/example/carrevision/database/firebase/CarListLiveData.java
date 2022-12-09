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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Car list live data class
 */
public class CarListLiveData extends BaseLiveData<List<CompleteCar>> {
    /**
     * Car list live data class constructor
     * @param reference Database reference
     */
    public CarListLiveData(DatabaseReference reference) {
        super("CarListLiveData", reference);
        setListener(new MyValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setValue(toCars(snapshot));
            }
        });
    }

    /**
     * Converts a data snapshot to a list of cars
     * @param snapshot Data snapshot
     * @return List of cars
     */
    private List<CompleteCar> toCars(DataSnapshot snapshot) {
        DataSnapshot carsSnapshot = snapshot.child(CarRepository.TABLE);
        List<CompleteCar> cars = new ArrayList<>();
        for (DataSnapshot carSnapshot : carsSnapshot.getChildren()) {
            if (carSnapshot.getKey() != null) {
                CompleteCar car = new CompleteCar();
                car.car = carSnapshot.getValue(CarEntity.class);
                if (car.car != null) {
                    car.car.setId(carSnapshot.getKey());
                    DataSnapshot modelSnapshot = snapshot.child(ModelRepository.TABLE).child(String.valueOf(car.car.getModelId()));
                    car.modelWithBrand = new ModelWithBrand();
                    car.modelWithBrand.model = modelSnapshot.getValue(ModelEntity.class);
                    if (car.modelWithBrand.model != null && modelSnapshot.getKey() != null) {
                        car.modelWithBrand.model.setId(Integer.parseInt(modelSnapshot.getKey()));
                        DataSnapshot brandSnapshot = snapshot.child(BrandRepository.TABLE).child(String.valueOf(car.modelWithBrand.model.getBrandId()));
                        car.modelWithBrand.brand = brandSnapshot.getValue(BrandEntity.class);
                        if (car.modelWithBrand.brand != null && brandSnapshot.getKey() != null) {
                            car.modelWithBrand.brand.setId(Integer.parseInt(brandSnapshot.getKey()));
                            cars.add(car);
                        }
                    }
                }
            }
        }
        Collections.sort(cars);
        return cars;
    }
}
