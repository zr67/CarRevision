package com.example.carrevision.ui.car;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.R;
import com.example.carrevision.adapter.CantonListAdapter;
import com.example.carrevision.adapter.ListAdapter;
import com.example.carrevision.adapter.StatusListAdapter;
import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.pojo.BrandWithModels;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.ui.SingleObjectActivity;
import com.example.carrevision.ui.revision.RevisionActivity;
import com.example.carrevision.ui.revision.RevisionsActivity;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.util.Status;
import com.example.carrevision.util.StringUtility;
import com.example.carrevision.viewmodel.canton.BrandModelListVM;
import com.example.carrevision.viewmodel.canton.CantonListVM;
import com.example.carrevision.viewmodel.car.CarVM;
import com.example.carrevision.viewmodel.revision.RevisionVM;
import com.example.carrevision.viewmodel.technician.TechnicianVM;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Car activity class
 */
public class CarActivity extends SingleObjectActivity {
    private static final String TAG = "CarActivity";

    private MenuItem menuItem;

    private Spinner spnBrand, spnModel, spnCantons, spnYear;
    private EditText etPlate, etMileage;

    private ListAdapter<BrandEntity> adapterBrand;
    private ListAdapter<ModelEntity> adapterModel;
    private CantonListAdapter adapterCantons;
    private ListAdapter<Integer> adapterYear;

    private CarVM carVM;
    private CompleteCar car;

    private List<BrandWithModels> brandWithModels;

    // OK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_car, frameLayout);
        initView();

        int carId = getIntent().getIntExtra("carId", -1);
        if (carId < 0) {
            setTitle(R.string.title_new_car);

            if (getIntent().hasExtra("cantonPosition") && getIntent().hasExtra("plate")) {
                spnCantons.setSelection(getIntent().getIntExtra("cantonPosition", 0));
                etPlate.setText(getIntent().getStringExtra("plate"));
            }
            switchMode();
        } else {
            setTitle(R.string.title_details_car);
        }

        CarVM.Factory factory = new CarVM.Factory(getApplication(), carId);
        carVM = new ViewModelProvider(new ViewModelStore(), factory).get(CarVM.class);
        carVM.getCar().observe(this, carEntity -> {
            if (carEntity != null) {
                car = carEntity;
                updateContent();
            }
        });
        setupVMs();
    }

    // OK
    private void startListActivity(int resource) {
        Intent intent = new Intent(CarActivity.this, CarsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("snackMsg", getString(resource));
        startActivity(intent);
    }

    // OK
    @Override
    protected void switchMode() {
        spnBrand.setFocusable(!editable);
        spnBrand.setEnabled(!editable);
        spnModel.setFocusable(!editable);
        spnModel.setEnabled(!editable);
        spnCantons.setFocusable(!editable);
        spnCantons.setEnabled(!editable);
        spnYear.setFocusable(!editable);
        spnYear.setEnabled(!editable);
        etPlate.setFocusable(!editable);
        etPlate.setEnabled(!editable);
        etMileage.setFocusable(!editable);
        etMileage.setEnabled(!editable);

        if (!editable) {
            spnBrand.setFocusableInTouchMode(true);
            spnModel.setFocusableInTouchMode(true);
            spnCantons.setFocusableInTouchMode(true);
            spnYear.setFocusableInTouchMode(true);
            etPlate.setFocusableInTouchMode(true);
            etMileage.setFocusableInTouchMode(true);

            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_done_white_24dp);
            }
        } else if (menuItem != null) {
            menuItem.setIcon(R.drawable.ic_edit_white_24dp);
        }
        editable = !editable;
    }

    // OK
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (car == null) {
            getMenuInflater().inflate(R.menu.apply, menu);
        } else if (technicianIsConnected()) {
            getMenuInflater().inflate(R.menu.edit_delete, menu);
            menuItem = menu.findItem(R.id.action_edit);
        }
        return super.onCreateOptionsMenu(menu);
    }

    // OK
    private void initView() {
        spnBrand = findViewById(R.id.et_rev_brand);
        spnModel = findViewById(R.id.et_rev_model);
        spnCantons = findViewById(R.id.spn_rev_canton);
        etPlate = findViewById(R.id.et_rev_plate);
        etMileage = findViewById(R.id.et_rev_mileage);
        spnYear = findViewById(R.id.et_rev_year);

        spnBrand.setFocusable(false);
        spnBrand.setEnabled(false);
        spnModel.setFocusable(false);
        spnModel.setEnabled(false);
        spnCantons.setFocusable(false);
        spnCantons.setEnabled(false);
        etPlate.setFocusable(false);
        etPlate.setEnabled(false);
        etMileage.setFocusable(false);
        etMileage.setEnabled(false);
        spnYear.setFocusable(false);
        spnYear.setEnabled(false);

        adapterBrand = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnBrand.setAdapter(adapterBrand);
        spnBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (brandWithModels != null){
                    for (BrandWithModels bm : brandWithModels) {
                        if (bm.brand.equals((BrandEntity) spnBrand.getSelectedItem())) {
                            adapterModel.updateData(bm.models);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterModel = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnModel.setAdapter(adapterModel);
        adapterCantons = new CantonListAdapter(this, R.layout.tv_list_view, new ArrayList<>());
        spnCantons.setAdapter(adapterCantons);

        ArrayList<Integer> years = new ArrayList<>();
        for (int i = 1950; i < 2023; i++){
            years.add(i);
        }

        adapterYear = new ListAdapter<>(this, R.layout.tv_list_view, years);
        spnYear.setAdapter(adapterYear);
    }

    // OK
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            boolean toggle = !editable||saveChanges();
            if (toggle) {
                if (!editable) {
                    Log.i(TAG, "Edit button clicked for the car " + car.car.getId());
                } else {
                    Log.i(TAG, "Done button clicked for the car " + car.car.getId());
                }
                switchMode();
            }
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            Log.i(TAG, "Delete button clicked for the car " + car.car.getId());
            getDeleteConfirmationDlg().show();
        } else if (item.getItemId() == R.id.action_apply) {
            Log.i(TAG, "Create button clicked");
            saveChanges();
            ignoreChanges = true;
        }
        return super.onOptionsItemSelected(item);
    }

    // OK
    @Override
    protected boolean saveChanges() {

        String plate = etPlate.getText().toString();
        String mileage = etMileage.getText().toString();

        if (TextUtils.isEmpty(plate)) {
            etPlate.setError(getString(R.string.err_plate_empty));
            etPlate.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mileage)) {
            etMileage.setError(getString(R.string.err_mileage_empty));
            etMileage.requestFocus();
            return false;
        }

        // Créer une var pour chacune des données utiliser pour créer voiture
        int idModel = ((ModelEntity) spnModel.getSelectedItem()).getId();
        plate = ((CantonEntity)spnCantons.getSelectedItem()).getAbbreviation() + etPlate.getText().toString();
        int m = Integer.parseInt(etMileage.getText().toString());
        int annee = (Integer) spnYear.getSelectedItem();

        // En cas de modif
        if (car != null) {
            CarEntity c = car.car;
            c.setModelId(idModel);
            c.setPlate(plate);
            c.setKilometers(m);
            c.setYear(new GregorianCalendar(annee, Calendar.JANUARY, 1).getTime());

            carVM.updateCar(c, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Car " + car.car.getId() + " successfully updated");
                    showSnack(R.string.car_update_success);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.w(TAG, "Failed to update the car " + car.car.getId());
                    showSnack(R.string.car_update_fail);
                }
            });
            // Nouvelle voiture à créer
        } else {
            Date date = new GregorianCalendar(annee, Calendar.JANUARY, 1).getTime();
            CarEntity rev = new CarEntity(idModel, plate, date, m);
            carVM.createCar(rev, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Car created successfully");
                    startListActivity(R.string.car_create_success);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.w(TAG, "Failed to create the car");
                    showSnack(R.string.car_create_fail);
                }
            });
        }
        return true;
    }

    // OK
    private void updateContent() {
        if (car != null){
            etPlate.setText(StringUtility.plateWithoutAbbreviation(car.car.getPlate()));
            etMileage.setText(car.car.getKilometers());
            String canton = StringUtility.abbreviationFromPlate(car.car.getPlate());
            spnCantons.post(() -> spnCantons.setSelection(adapterCantons.getPosition( new CantonEntity(canton, canton))));
            spnBrand.post(() -> spnBrand.setSelection(adapterBrand.getPosition(car.modelWithBrand.brand)));
            spnModel.post(() -> spnModel.setSelection(adapterModel.getPosition(car.modelWithBrand.model)));
            spnYear.post(() -> spnYear.setSelection(adapterYear.getPosition(car.car.getYear().getYear())));
        }
    }

    // OK
    @Override
    protected boolean hasChanges() {
        boolean rv;
        if (car != null) {

            rv = !((CantonEntity) spnCantons.getSelectedItem()).getAbbreviation().equals(StringUtility.abbreviationFromPlate(car.car.getPlate()))
                    || ! ((BrandEntity) spnBrand.getSelectedItem()).equals(car.modelWithBrand.brand)
                    || ! ((ModelEntity) spnModel.getSelectedItem()).equals(car.modelWithBrand.model)
                    || ((Integer) spnYear.getSelectedItem()) != car.car.getYear().getYear()
                    || Integer.parseInt(etMileage.getText().toString()) != car.car.getKilometers()
                    || !etPlate.getText().toString().equals(StringUtility.plateWithoutAbbreviation(car.car.getPlate()));
        } else {
            rv = spnCantons.getSelectedItem() != null
                    || spnBrand.getSelectedItem() != null
                    || spnModel.getSelectedItem() != null
                    || spnYear.getSelectedItem() != null
                    || !etPlate.getText().toString().trim().equals("")
                    || !etMileage.getText().toString().trim().equals("");
        }
        return rv;
    }

    // OK
    @Override
    protected void deleteItem() {
        if (car != null) {
            carVM.deleteCar(car.car, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Car " + car.car.getId() + " successfully deleted");
                    startListActivity(R.string.car_delete_success);
                }
                @Override
                public void onFailure(Exception e) {
                    Log.w(TAG, "Failed to delete the car " + car.car.getId());
                    showSnack(R.string.car_delete_fail);
                }
            });
        }
    }

    //
    private void setupVMs() {

        BrandModelListVM.Factory bmFact = new BrandModelListVM.Factory(getApplication());
        BrandModelListVM brandModelListVM = new ViewModelProvider(new ViewModelStore(), bmFact).get(BrandModelListVM.class);
        brandModelListVM.getBrandWithModels().observe(this, brandsModels -> {
            if (brandsModels != null) {
                ArrayList<BrandEntity> brands = new ArrayList<>();
                for (BrandWithModels bm : brandsModels) {
                    brands.add(bm.brand);
                }
                adapterBrand.updateData(brands);
                brandWithModels = brandWithModels;
            }
        });

        CantonListVM.Factory cFact = new CantonListVM.Factory(getApplication());
        CantonListVM cantonsVM = new ViewModelProvider(new ViewModelStore(), cFact).get(CantonListVM.class);
        cantonsVM.getCantons().observe(this, cantonEntities -> {
            if (cantonEntities != null) {
                adapterCantons.updateData(cantonEntities);
            }
        });
    }






}