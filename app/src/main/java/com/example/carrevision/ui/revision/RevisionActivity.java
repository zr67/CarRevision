package com.example.carrevision.ui.revision;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.R;
import com.example.carrevision.adapter.CantonListAdapter;
import com.example.carrevision.adapter.ListAdapter;
import com.example.carrevision.adapter.StatusListAdapter;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.util.Status;
import com.example.carrevision.util.StringUtility;
import com.example.carrevision.viewmodel.canton.CantonListVM;
import com.example.carrevision.viewmodel.car.CarListVM;
import com.example.carrevision.viewmodel.revision.RevisionVM;
import com.example.carrevision.viewmodel.technician.TechnicianListVM;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Revision activity class
 */
public class RevisionActivity extends BaseActivity {
    private static final String TAG = "RevisionActivity";
    private boolean noUp;
    private boolean editable;
    private boolean hasChanges;

    private ImageButton bAddCar;
    private Spinner spnCantons, spnStatus, spnTechnician;
    private EditText etPlate, etBrand, etModel, etMileage, etYear, etDateStart, etDateEnd;

    private Toast toast;

    private CantonListAdapter adapterCantons;
    private StatusListAdapter adapterStatus;
    private ListAdapter<TechnicianEntity> adapterTechnician;

    private RevisionVM revisionVM;
    private TechnicianListVM techniciansVM;
    private CantonListVM cantonsVM;
    private CarListVM carsVM;

    private CompleteRevision revision;
    private List<CompleteCar> cars;
    private CompleteCar matchingCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_revision, frameLayout);
        initView();

        noUp = false;
        hasChanges = false;
        int revisionId = getIntent().getIntExtra("revisionId", -1);
        if (revisionId <= 0) {
            setTitle("Create Revision");
            matchingCar = null;
            switchMode();
        }
        else {
            setTitle("Revision Details");
            RevisionVM.Factory factory = new RevisionVM.Factory(getApplication(), revisionId);
            revisionVM = new ViewModelProvider(new ViewModelStore(), factory).get(RevisionVM.class);
            revisionVM.getRevision().observe(this, revisionEntity -> {
                if (revisionEntity != null) {
                    revision = revisionEntity;
                    matchingCar = revision.completeCar;
                    updateContent();
                }
            });
        }

        CarListVM.Factory caFact = new CarListVM.Factory(getApplication());
        carsVM = new ViewModelProvider(new ViewModelStore(), caFact).get(CarListVM.class);
        carsVM.getCars().observe(this, carEntities -> {
            if (carEntities != null) {
                cars = carEntities;
            }
        });
        setupSpinners();
        setupSpinnerVMs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (revision != null) {
            getMenuInflater().inflate(R.menu.edit_delete, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.apply, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            if (!editable) {
                Log.i(TAG, "Edit button clicked for the revision " + revision.revision.getId());
                item.setIcon(R.drawable.ic_done_white_24dp);
            }
            else {
                Log.i(TAG, "Done button clicked for the revision " + revision.revision.getId());
                item.setIcon(R.drawable.ic_edit_white_24dp);
            }
            switchMode();
        }
        else if (item.getItemId() == R.id.action_delete) {
            Log.e(TAG, "Delete button clicked for the revision " + revision.revision.getId());
        }
        else if (item.getItemId() == R.id.action_apply) {
            Log.e(TAG, "Create button clicked");
        }
        else {//if (hasChanges) {
            // ask to save or discard
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Prepares the spinners
     */
    private void setupSpinners() {
        adapterCantons = new CantonListAdapter(this, R.layout.tv_list_view, new ArrayList<>());
        spnCantons.setAdapter(adapterCantons);
        adapterStatus = new StatusListAdapter(this, R.layout.tv_list_view, Status.getAllStatus());
        spnStatus.setAdapter(adapterStatus);
        adapterTechnician = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnTechnician.setAdapter(adapterTechnician);
    }

    /**
     * Sets up the spinner view-models
     */
    private void setupSpinnerVMs() {
        CantonListVM.Factory cFact = new CantonListVM.Factory(getApplication());
        cantonsVM = new ViewModelProvider(new ViewModelStore(), cFact).get(CantonListVM.class);
        cantonsVM.getCantons().observe(this, cantonEntities -> {
            if (cantonEntities != null) {
                adapterCantons.updateData(cantonEntities);
            }
        });
        TechnicianListVM.Factory tFact = new TechnicianListVM.Factory(getApplication());
        techniciansVM = new ViewModelProvider(new ViewModelStore(), tFact).get(TechnicianListVM.class);
        techniciansVM.getTechnicians().observe(this, technicianEntities -> {
            if (technicianEntities != null) {
                adapterTechnician.updateData(technicianEntities);
            }
        });
    }

    /**
     * Saves the changes made to the revision
     */
    private void saveChanges(String startDate, String endDate, Status status, TechnicianEntity technician) {
        RevisionEntity rev = revision.revision;
        //if (StringUtility.isValidDate(startDate)) {
        //    rev.setStart(StringUtility.dateFromString(startDate));
        //}
        //if (StringUtility.isValidDate(endDate)) {
        //    rev.setEnd(StringUtility.dateFromString(endDate));
        //}
        //else {
        //    rev.setEnd(null);
        //}
        rev.setStatus(status);
        rev.setTechnicianId(technician.getId());
        revisionVM.updateRevision(rev, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Revision " + revision.revision.getId() + " successfully updated");
                toast = Toast.makeText(getApplicationContext(), getString(R.string.revision_update_success), Toast.LENGTH_LONG);
                toast.show();
            }
            @Override
            public void onFailure(Exception e) {
                Log.w(TAG, "Failed to update the revision " + revision.revision.getId());
                toast = Toast.makeText(getApplicationContext(), getString(R.string.revision_update_fail), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    /**
     * Switches between the edit and the read-only mode
     */
    private void switchMode() {
        if (editable) {
            saveChanges(etDateStart.getText().toString(), etDateEnd.getText().toString(), (Status) spnStatus.getSelectedItem(), (TechnicianEntity) spnTechnician.getSelectedItem());
        }
        spnCantons.setFocusable(!editable);
        spnCantons.setEnabled(!editable);
        etPlate.setFocusable(!editable);
        etPlate.setEnabled(!editable);
        etDateStart.setFocusable(!editable);
        etDateStart.setEnabled(!editable);
        etDateEnd.setFocusable(!editable);
        etDateEnd.setEnabled(!editable);
        spnStatus.setFocusable(!editable);
        spnStatus.setEnabled(!editable);
        spnTechnician.setFocusable(!editable);
        spnTechnician.setEnabled(!editable);
        if (!editable) {
            spnCantons.setFocusableInTouchMode(true);
            etPlate.setFocusableInTouchMode(true);
            etDateStart.setFocusableInTouchMode(true);
            etDateEnd.setFocusableInTouchMode(true);
            spnStatus.setFocusableInTouchMode(true);
            spnTechnician.setFocusableInTouchMode(true);
        }
        editable = !editable;
    }

    /**
     * Updates the content with the current revision
     */
    private void updateContent() {
        if (revision != null) {
            noUp = true;
            String canton = StringUtility.abbreviationFromPlate(revision.completeCar.car.getPlate());
            spnCantons.setSelection(adapterCantons.getPosition(new CantonEntity(canton, canton)));
            etPlate.setText(StringUtility.plateWithoutAbbreviation(revision.completeCar.car.getPlate()));
            etBrand.setText(revision.completeCar.modelWithBrand.brand.getBrand());
            etModel.setText(revision.completeCar.modelWithBrand.model.getModel());
            etMileage.setText(StringUtility.intToString(revision.completeCar.car.getKilometers(), getApplicationContext()));
            etYear.setText(StringUtility.dateToYearString(revision.completeCar.car.getYear(), getApplicationContext()));
            etDateStart.setText(StringUtility.dateToDateTimeString(revision.revision.getStart(), getApplicationContext()));
            Date end = revision.revision.getEnd();
            if (end != null) {
                etDateEnd.setText(StringUtility.dateToDateTimeString(end, getApplicationContext()));
            }
            spnStatus.setSelection(adapterStatus.getPosition(revision.revision.getStatus()));
            spnTechnician.setSelection(adapterTechnician.getPosition(revision.technician));
            noUp = false;
        }
    }

    /**
     * Searches for an existing car matching the plate entered by the user
     */
    private void searchForMatchingCar() {
        for (CompleteCar cc : cars) {
            String abbr = ((CantonEntity)spnCantons.getSelectedItem()).getAbbreviation();
            if (cc.car.getPlate().equals(abbr + etPlate.getText().toString())) {
                matchingCar = cc;
                bAddCar.setImageResource(R.drawable.ic_done_white_24dp);
                return;
            }
        }
        matchingCar = null;
        bAddCar.setImageResource(R.drawable.ic_action_add);
    }

    /**
     * Initializes the view
     */
    private void initView() {
        editable = false;
        spnCantons = findViewById(R.id.spn_rev_canton);
        etPlate = findViewById(R.id.et_rev_plate);
        etPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (!noUp) {
                    searchForMatchingCar();
                }
            }
        });
        bAddCar = findViewById(R.id.b_checkPlate);
        etBrand = findViewById(R.id.et_rev_brand);
        etModel = findViewById(R.id.et_rev_model);
        etMileage = findViewById(R.id.et_rev_mileage);
        etYear = findViewById(R.id.et_rev_year);
        etDateStart = findViewById(R.id.et_rev_date_start);
        etDateEnd = findViewById(R.id.et_rev_date_end);
        spnStatus = findViewById(R.id.spn_rev_status);
        spnTechnician = findViewById(R.id.spn_rev_technician);

        spnCantons.setFocusable(false);
        spnCantons.setEnabled(false);
        etPlate.setFocusable(false);
        etPlate.setEnabled(false);
        bAddCar.setEnabled(false);
        bAddCar.setOnClickListener(view -> {
            if (matchingCar != null) {
                revision.revision.setCarId(matchingCar.car.getId());
            }
            else {
                /*Intent intent = new Intent(RevisionActivity.this, CarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("plate", ((CantonEntity) spnCantons.getSelectedItem()).getAbbreviation() + etPlate.getText().toString());
                startActivity(intent);*/
            }
        });
        etBrand.setFocusable(false);
        etBrand.setEnabled(false);
        etModel.setFocusable(false);
        etModel.setEnabled(false);
        etMileage.setFocusable(false);
        etMileage.setEnabled(false);
        etYear.setFocusable(false);
        etYear.setEnabled(false);
        etDateStart.setFocusable(false);
        etDateStart.setEnabled(false);
        etDateEnd.setFocusable(false);
        etDateEnd.setEnabled(false);
        spnStatus.setFocusable(false);
        spnStatus.setEnabled(false);
        spnTechnician.setFocusable(false);
        spnTechnician.setEnabled(false);
    }
}
