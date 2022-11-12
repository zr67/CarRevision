package com.example.carrevision.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carrevision.BaseApp;
import com.example.carrevision.database.pojo.BrandWithModels;

import java.util.List;

public class BrandRepository {

    private static volatile BrandRepository instance;

    private BrandRepository() {}

    public static BrandRepository getInstance(){
        if (instance == null) {
            synchronized (BrandRepository.class) {
                if (instance == null) {
                    instance = new BrandRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<BrandWithModels>> getBrandWithModels(Application application) {
        return ((BaseApp) application).getDatabase().brandDao().getAll();
    }
}


