package com.example.carrevision.ui.revision;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.R;
import com.example.carrevision.adapter.ListAdapter;
import com.example.carrevision.database.entity.CantonEntity;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.StringUtility;
import com.example.carrevision.viewmodel.canton.CantonListVM;
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

    private static final int CREATE_REVISION = 0;
    private static final int EDIT_REVISION = 1;
    private static final int DELETE_REVISION = 2;

    private Toast toast;
    private boolean editable;

    private Spinner spnCantons;
    private EditText etPlate;
    private Button bAddCar;
    private EditText etBrand;
    private EditText etModel;
    private EditText etMileage;
    private EditText etYear;
    private EditText etDateStart;
    private EditText etDateEnd;
    private Spinner spnStatus;
    private Spinner spnTechnician;

    private ListAdapter<CantonEntity> adapterCantons;
    private ListAdapter<String> adapterStatus;
    private ListAdapter<TechnicianEntity> adapterTechnician;

    private RevisionVM revisionVM;
    private TechnicianListVM techniciansVM;
    private CantonListVM cantonsVM;
    private CompleteRevision revision;
    private List<CantonEntity> cantons;
    private List<TechnicianEntity> technicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_revision, frameLayout);
        initView();

        CantonListVM.Factory cFact = new CantonListVM.Factory(getApplication());
        cantonsVM = new ViewModelProvider(new ViewModelStore(), cFact).get(CantonListVM.class);
        cantonsVM.getCantons().observe(this, cantonEntities -> {
            if (cantonEntities != null) {
                cantons = cantonEntities;
            }
        });
        TechnicianListVM.Factory tFact = new TechnicianListVM.Factory(getApplication());
        techniciansVM = new ViewModelProvider(new ViewModelStore(), tFact).get(TechnicianListVM.class);
        techniciansVM.getTechnicians().observe(this, technicianEntities -> {
            if (technicianEntities != null) {
                technicians = technicianEntities;
            }
        });

        String rev = getIntent().getStringExtra("revisionId");
        if (rev == null) {
            setTitle("Create Revision");
            switchMode();
        }
        else {
            int revisionId = Integer.parseInt(rev);
            RevisionVM.Factory factory = new RevisionVM.Factory(getApplication(), revisionId);
            revisionVM = new ViewModelProvider(new ViewModelStore(), factory).get(RevisionVM.class);
            revisionVM.getRevision().observe(this, revisionEntity -> {
                if (revisionEntity != null) {
                    revision = revisionEntity;
                    updateContent();
                }
            });
            setTitle("Revision Details");
        }

        setupCantonsSpinner();
        setupStatusSpinner();
        setupTechnicianSpinner();
    }

    /**
     * Prepares the cantons spinner
     */
    private void setupCantonsSpinner() {
        Log.e("cantons", cantons == null ? "null" : "nn");
        adapterCantons = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnCantons.setAdapter(adapterCantons);
    }

    /**
     * Prepares the status spinner
     */
    private void setupStatusSpinner() {
        //adapterStatus = new ListAdapter<>(this, R.layout.tv_list_view, );
        //spnStatus.setAdapter(adapterStatus);
    }

    /**
     * Prepares the technician spinner
     */
    private void setupTechnicianSpinner() {
        Log.e("technicians", technicians == null ? "null" : "nn");
        adapterTechnician = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnTechnician.setAdapter(adapterTechnician);
    }

    /**
     * Saves the changes made to the revision
     */
    private void saveChanges() {

    }

    /**
     * Switches between the edit and the read-only mode
     */
    private void switchMode() {
        if (editable) {
            saveChanges();
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
            //spnCantons
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
            //spnStatus
            //spnTechnicians
        }
    }

    /**
     * Initializes the view
     */
    private void initView() {
        editable = false;
        spnCantons = findViewById(R.id.spinner_rev_canton);
        etPlate = findViewById(R.id.et_rev_plate);
        bAddCar = findViewById(R.id.b_rev_add_car);
        etBrand = findViewById(R.id.et_rev_brand);
        etModel = findViewById(R.id.et_rev_model);
        etMileage = findViewById(R.id.et_rev_mileage);
        etYear = findViewById(R.id.et_rev_year);
        etDateStart = findViewById(R.id.et_rev_date_start);
        etDateEnd = findViewById(R.id.et_rev_date_end);
        spnStatus = findViewById(R.id.spinner_rev_status);
        spnTechnician = findViewById(R.id.spinner_rev_technician);

        spnCantons.setFocusable(false);
        spnCantons.setEnabled(false);
        etPlate.setFocusable(false);
        etPlate.setEnabled(false);
        bAddCar.setEnabled(false);
        bAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
