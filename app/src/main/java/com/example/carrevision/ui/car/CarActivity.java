package com.example.carrevision.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.R;
import com.example.carrevision.adapter.CantonListAdapter;
import com.example.carrevision.adapter.ListAdapter;
import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.ui.SingleObjectActivity;
import com.example.carrevision.ui.revision.RevisionActivity;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.util.StringUtility;
import com.example.carrevision.viewmodel.brand.BrandListVM;
import com.example.carrevision.viewmodel.canton.CantonListVM;
import com.example.carrevision.viewmodel.car.CarVM;
import com.example.carrevision.viewmodel.model.ModelListVM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Car activity class
 */
public class CarActivity extends SingleObjectActivity {
    private static final String TAG = "CarActivity";

    private MenuItem menuItem;
    private boolean redirectToRevision;

    private Spinner spnBrand, spnModel, spnCantons, spnYear;
    private EditText etPlate, etMileage, etBrand, etModel, etCanton, etYear;

    private ListAdapter<BrandEntity> adapterBrand;
    private ListAdapter<ModelEntity> adapterModel;
    private CantonListAdapter adapterCantons;
    private ListAdapter<String> adapterYear;

    private CarVM carVM;
    private CompleteCar car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redirectToRevision = false;
        getLayoutInflater().inflate(R.layout.activity_car, frameLayout);
        initView();

        int carId = getIntent().getIntExtra("carId", -1);
        if (carId < 0) {
            setTitle(R.string.title_new_car);
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
        if (getIntent().hasExtra("cantonPosition") && getIntent().hasExtra("plate")) {
            redirectToRevision = true;
            etPlate.setText(getIntent().getStringExtra("plate"));
        }
    }

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

    @Override
    protected void startListActivity(int resource) {
        Intent intent = new Intent(CarActivity.this, CarsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("snackMsg", getString(resource));
        startActivity(intent);
    }

    @Override
    protected void setupVMs() {
        BrandListVM.Factory bmFact = new BrandListVM.Factory(getApplication());
        BrandListVM brandModelListVM = new ViewModelProvider(new ViewModelStore(), bmFact).get(BrandListVM.class);
        brandModelListVM.getBrands().observe(this, brandEntities -> {
            if (brandEntities != null) {
                adapterBrand.updateData(brandEntities);
            }
        });
        CantonListVM.Factory cFact = new CantonListVM.Factory(getApplication());
        CantonListVM cantonsVM = new ViewModelProvider(new ViewModelStore(), cFact).get(CantonListVM.class);
        cantonsVM.getCantons().observe(this, cantonEntities -> {
            if (cantonEntities != null) {
                adapterCantons.updateData(cantonEntities);
                spnCantons.setSelection(getIntent().getIntExtra("cantonPosition", 0));
            }
        });
    }

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
        int year = Integer.parseInt(spnYear.getSelectedItem().toString());

        // En cas de modif
        if (car != null) {
            CarEntity c = car.car;
            c.setModelId(idModel);
            c.setPlate(plate);
            c.setKilometers(m);
            c.setYear(new GregorianCalendar(year, Calendar.JANUARY, 1).getTime());

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
            Date date = new GregorianCalendar(year, Calendar.JANUARY, 1).getTime();
            CarEntity rev = new CarEntity(idModel, plate, date, m);
            carVM.createCar(rev, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Car created successfully");
                    if (!redirectToRevision) {
                        startListActivity(R.string.car_create_success);
                    } else {
                        Intent intent = new Intent(CarActivity.this, RevisionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("cantonPosition", spnCantons.getSelectedItemPosition());
                        intent.putExtra("plate", etPlate.getText().toString());
                        intent.putExtra("statusPosition", getIntent().getIntExtra("statusPosition", 0));
                        intent.putExtra("technicianPosition", getIntent().getIntExtra("technicianPosition", technicianIsConnected() ? getConnectedTechnicianId() : 0));
                        intent.putExtra("start", getIntent().getStringExtra("start"));
                        intent.putExtra("end", getIntent().getStringExtra("end"));
                        intent.putExtra("snackMsg", getString(R.string.car_create_success));
                        startActivity(intent);
                        finish();
                    }
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

    @Override
    protected void switchMode() {
        etBrand.setEnabled(!editable);
        spnBrand.setEnabled(!editable);
        etModel.setEnabled(!editable);
        spnModel.setEnabled(!editable);
        etCanton.setEnabled(!editable);
        spnCantons.setEnabled(!editable);
        etYear.setEnabled(!editable);
        spnYear.setEnabled(!editable);
        etPlate.setFocusable(!editable);
        etPlate.setEnabled(!editable);
        etMileage.setFocusable(!editable);
        etMileage.setEnabled(!editable);
        if (!editable) {
            etPlate.setFocusableInTouchMode(true);
            etMileage.setFocusableInTouchMode(true);
            etBrand.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            etModel.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            etCanton.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            etYear.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_done_white_24dp);
            }
        } else {
            etBrand.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            etModel.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            etCanton.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            etYear.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_edit_white_24dp);
            }
        }
        editable = !editable;
    }

    @Override
    protected void initView() {
        spnBrand = findViewById(R.id.spn_car_brand);
        spnModel = findViewById(R.id.spn_car_model);
        spnCantons = findViewById(R.id.spn_car_canton);
        etPlate = findViewById(R.id.et_car_plate);
        etMileage = findViewById(R.id.et_car_mileage);
        spnYear = findViewById(R.id.spn_car_year);
        etBrand = findViewById(R.id.et_car_brand);
        etModel = findViewById(R.id.et_car_model);
        etYear = findViewById(R.id.et_car_year);
        etCanton = findViewById(R.id.et_car_canton);

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
        etBrand.setFocusable(false);
        etBrand.setEnabled(false);
        etModel.setFocusable(false);
        etModel.setEnabled(false);
        etYear.setEnabled(false);
        etYear.setFocusable(false);
        etCanton.setEnabled(false);
        etCanton.setFocusable(false);

        etBrand.setOnClickListener((view) -> spnBrand.performClick());
        etModel.setOnClickListener((view) -> spnModel.performClick());
        etYear.setOnClickListener((view) -> spnYear.performClick());
        etCanton.setOnClickListener((view) -> spnCantons.performClick());

        adapterBrand = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnBrand.setAdapter(adapterBrand);
        spnBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etBrand.setText(spnBrand.getSelectedItem().toString());
                ModelListVM.Factory factory = new ModelListVM.Factory(getApplication(), ((BrandEntity)spnBrand.getSelectedItem()).getId());
                ModelListVM modelListVM = new ViewModelProvider(new ViewModelStore(), factory).get(ModelListVM.class);
                modelListVM.getModelsByBrandId().observe(CarActivity.this, modelEntities -> {
                    if (modelEntities != null) {
                        adapterModel.updateData(modelEntities);
                        if (car != null) {
                            if (car.modelWithBrand.brand.equals(spnBrand.getSelectedItem())) {
                                spnModel.setSelection(adapterModel.getPosition(car.modelWithBrand.model));
                            }
                        }
                        etModel.setText(modelEntities.get(0).toString());
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        adapterModel = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnModel.setAdapter(adapterModel);
        spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etModel.setText(spnModel.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        adapterCantons = new CantonListAdapter(this, R.layout.tv_list_view, new ArrayList<>());
        spnCantons.setAdapter(adapterCantons);
        spnCantons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etCanton.setText(spnCantons.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayList<String> years = new ArrayList<>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 1950; i--){
            years.add(String.valueOf(i));
        }
        adapterYear = new ListAdapter<>(this, R.layout.tv_list_view, years);
        spnYear.setAdapter(adapterYear);
        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etYear.setText(spnYear.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void updateContent() {
        if (car != null){
            etPlate.setText(StringUtility.plateWithoutAbbreviation(car.car.getPlate()));
            etMileage.setText(String.valueOf(car.car.getKilometers()));
            String canton = StringUtility.abbreviationFromPlate(car.car.getPlate());
            spnCantons.post(() -> spnCantons.setSelection(adapterCantons.getPosition( new CantonEntity(canton, canton))));
            spnBrand.post(() -> spnBrand.setSelection(adapterBrand.getPosition(car.modelWithBrand.brand)));
            spnModel.post(() -> spnModel.setSelection(adapterModel.getPosition(car.modelWithBrand.model)));
            spnYear.post(() -> spnYear.setSelection(adapterYear.getPosition(StringUtility.dateToYearString(car.car.getYear(), this))));
        }
    }

    @Override
    protected boolean hasChanges() {
        boolean rv;
        if (car != null) {

            rv = !((CantonEntity) spnCantons.getSelectedItem()).getAbbreviation().equals(StringUtility.abbreviationFromPlate(car.car.getPlate()))
                    || ! spnBrand.getSelectedItem().equals(car.modelWithBrand.brand)
                    || ! spnModel.getSelectedItem().equals(car.modelWithBrand.model)
                    || !spnYear.getSelectedItem().toString().equals(StringUtility.dateToYearString(car.car.getYear(), this))
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
}