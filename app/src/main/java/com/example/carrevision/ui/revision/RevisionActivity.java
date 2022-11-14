package com.example.carrevision.ui.revision;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

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
import com.example.carrevision.ui.SingleObjectActivity;
import com.example.carrevision.ui.car.CarActivity;
import com.example.carrevision.util.OnAsyncEventListener;
import com.example.carrevision.util.Status;
import com.example.carrevision.util.StringUtility;
import com.example.carrevision.viewmodel.canton.CantonListVM;
import com.example.carrevision.viewmodel.car.CarListVM;
import com.example.carrevision.viewmodel.revision.RevisionVM;
import com.example.carrevision.viewmodel.technician.TechnicianListVM;
import com.example.carrevision.viewmodel.technician.TechnicianVM;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Revision activity class
 */
public class RevisionActivity extends SingleObjectActivity {
    private static final String TAG = "RevisionActivity";

    private ImageButton bCheckPlate;
    private Spinner spnCantons, spnStatus, spnTechnician;
    private EditText etPlate, etBrand, etModel, etMileage, etYear, etDateStart, etDateEnd, etTechnician, etStatus, etCanton;
    private MenuItem miAction;

    private CantonListAdapter adapterCantons;
    private StatusListAdapter adapterStatus;
    private ListAdapter<TechnicianEntity> adapterTechnician;

    private RevisionVM revisionVM;

    private CompleteRevision revision;
    private List<CompleteCar> cars;
    private CompleteCar matchingCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_revision, frameLayout);
        initView();

        int revisionId = getIntent().getIntExtra("revisionId", -1);
        if (revisionId < 0) {
            setTitle(R.string.title_new_revision);
            matchingCar = null;
            switchMode();
        } else {
            setTitle(R.string.title_details_revision);
        }
        RevisionVM.Factory factory = new RevisionVM.Factory(getApplication(), revisionId);
        revisionVM = new ViewModelProvider(new ViewModelStore(), factory).get(RevisionVM.class);
        revisionVM.getRevision().observe(this, revisionEntity -> {
            if (revisionEntity != null) {
                revision = revisionEntity;
                matchingCar = revision.completeCar;
                updateContent();
            }
        });
        if (getIntent().hasExtra("cantonPosition") && getIntent().hasExtra("plate")) {
            etPlate.setText(getIntent().getStringExtra("plate"));
        }
        if (getIntent().hasExtra("start")) {
            etDateStart.setText(getIntent().getStringExtra("start"));
        }
        if (getIntent().hasExtra("end")) {
            etDateEnd.setText(getIntent().getStringExtra("end"));
        }
        setupVMs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (revision == null) {
            getMenuInflater().inflate(R.menu.apply, menu);
        } else if (revision.technician.getId() == getConnectedTechnicianId() || technicianIsAdmin()) {
            getMenuInflater().inflate(R.menu.edit_delete, menu);
            miAction = menu.findItem(R.id.action_edit);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            boolean toggle = !editable||saveChanges();
            if (toggle) {
                if (!editable) {
                    Log.i(TAG, "Edit button clicked for the revision " + revision.revision.getId());
                } else {
                    Log.i(TAG, "Done button clicked for the revision " + revision.revision.getId());
                }
                switchMode();
            }
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            Log.i(TAG, "Delete button clicked for the revision " + revision.revision.getId());
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
        Intent intent = new Intent(RevisionActivity.this, RevisionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("snackMsg", getString(resource));
        startActivity(intent);
    }

    @Override
    protected void setupVMs() {
        CarListVM.Factory caFact = new CarListVM.Factory(getApplication());
        CarListVM carsVM = new ViewModelProvider(new ViewModelStore(), caFact).get(CarListVM.class);
        carsVM.getCars().observe(this, carEntities -> {
            if (carEntities != null) {
                cars = carEntities;
            }
        });
        CantonListVM.Factory cFact = new CantonListVM.Factory(getApplication());
        CantonListVM cantonsVM = new ViewModelProvider(new ViewModelStore(), cFact).get(CantonListVM.class);
        cantonsVM.getCantons().observe(this, cantonEntities -> {
            if (cantonEntities != null) {
                adapterCantons.updateData(cantonEntities);
                spnCantons.setSelection(getIntent().getIntExtra("cantonPosition", 0));
                if (matchingCar != null) {
                    bCheckPlate.callOnClick();
                }
            }
        });
        TechnicianListVM.Factory tFact = new TechnicianListVM.Factory(getApplication());
        TechnicianListVM techniciansVM = new ViewModelProvider(new ViewModelStore(), tFact).get(TechnicianListVM.class);
        techniciansVM.getTechnicians().observe(this, technicianEntities -> {
            if (technicianEntities != null) {
                adapterTechnician.updateData(technicianEntities);
                if (technicianIsAdmin()) {
                    spnTechnician.setSelection(getIntent().getIntExtra("technicianPosition", 0));
                } else if (technicianIsConnected()) {
                    TechnicianVM.Factory factory = new TechnicianVM.Factory(getApplication(), getConnectedTechnicianId());
                    TechnicianVM technicianVM = new ViewModelProvider(new ViewModelStore(), factory).get(TechnicianVM.class);
                    technicianVM.getTechnician().observe(this, technicianEntity -> {
                        if (technicianEntity != null) {
                            spnTechnician.setSelection(adapterTechnician.getPosition(technicianEntity));
                        }
                    });
                }
            }
        });
    }

    @Override
    protected boolean saveChanges() {
        boolean rv = false;
        if (matchingCar != null) {
            Date start, end;
            boolean valid = true;
            try {
                start = StringUtility.dateTimeStringToDate(etDateStart.getText().toString(), this);
            } catch (ParseException e) {
                start = null;
            }
            try {
                end = StringUtility.dateTimeStringToDate(etDateEnd.getText().toString(), this);
            } catch (ParseException e) {
                Log.w(TAG, "End date entered not valid. Incorrect format input.");
                etDateEnd.setError(getString(R.string.date_end_invalid) + "\n" + String.format(getString(R.string.use_format), StringUtility.getDateFormatPattern(this)));
                end = null;
                valid = false;
            }
            if (start == null) {
                Log.w(TAG, "Start date entered not valid. Incorrect format or empty input");
                etDateStart.setError(getString(R.string.date_start_invalid) + "\n" + String.format(getString(R.string.use_format), StringUtility.getDateFormatPattern(this)));
                valid = false;
            }
            rv = valid;
            if (valid) {
                bCheckPlate.callOnClick();
                int technicianId = ((TechnicianEntity) spnTechnician.getSelectedItem()).getId();
                int carId = matchingCar.car.getId();
                Status status = (Status) spnStatus.getSelectedItem();
                if (revision != null) {
                    RevisionEntity rev = revision.revision;
                    rev.setCarId(carId);
                    rev.setStart(start);
                    rev.setEnd(end);
                    rev.setStatus(status);
                    rev.setTechnicianId(technicianId);
                    revisionVM.updateRevision(rev, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Revision " + revision.revision.getId() + " successfully updated");
                            showSnack(R.string.revision_update_success);
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Log.w(TAG, "Failed to update the revision " + revision.revision.getId());
                            showSnack(R.string.revision_update_fail);
                        }
                    });
                } else {
                    RevisionEntity rev = new RevisionEntity(technicianId, carId, start, end, status);
                    revisionVM.createRevision(rev, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Revision created successfully");
                            startListActivity(R.string.revision_create_success);
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Log.w(TAG, "Failed to create the revision");
                            showSnack(R.string.revision_create_fail);
                        }
                    });
                }
            }
        }
        else {
            showSnack(R.string.revision_car_not_existing);
            bCheckPlate.callOnClick();
        }
        return rv;
    }

    @Override
    protected void switchMode() {
        spnCantons.setEnabled(!editable);
        etPlate.setFocusable(!editable);
        etPlate.setEnabled(!editable);
        etDateStart.setFocusable(!editable);
        etDateStart.setEnabled(!editable);
        etDateEnd.setFocusable(!editable);
        etDateEnd.setEnabled(!editable);
        etStatus.setEnabled(!editable);
        etCanton.setEnabled(!editable);
        spnStatus.setEnabled(!editable);
        if (technicianIsAdmin()) {
            etTechnician.setEnabled(!editable);
        }
        bCheckPlate.setEnabled(!editable);
        if (!editable) {
            etPlate.setFocusableInTouchMode(true);
            etDateStart.setFocusableInTouchMode(true);
            etDateEnd.setFocusableInTouchMode(true);
            etStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            etCanton.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            if (technicianIsAdmin()) {
                etTechnician.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrow, null);
            }
            if (miAction != null) {
                miAction.setIcon(R.drawable.ic_done_white_24dp);
            }
        } else {
            etTechnician.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            etStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            etCanton.setCompoundDrawablesWithIntrinsicBounds(null, null, dArrowDisabled, null);
            if (miAction != null) {
                miAction.setIcon(R.drawable.ic_edit_white_24dp);
            }
        }
        editable = !editable;
    }

    @Override
    protected void initView() {
        bCheckPlate = findViewById(R.id.b_checkPlate);
        etPlate = findViewById(R.id.et_rev_plate);
        etBrand = findViewById(R.id.et_rev_brand);
        etModel = findViewById(R.id.et_rev_model);
        etMileage = findViewById(R.id.et_rev_mileage);
        etYear = findViewById(R.id.et_rev_year);
        etDateStart = findViewById(R.id.et_rev_date_start);
        etDateEnd = findViewById(R.id.et_rev_date_end);
        etTechnician = findViewById(R.id.et_rev_technician);
        etStatus = findViewById(R.id.et_rev_status);
        etCanton = findViewById(R.id.et_rev_canton);
        spnCantons = findViewById(R.id.spn_rev_canton);
        spnStatus = findViewById(R.id.spn_rev_status);
        spnTechnician = findViewById(R.id.spn_rev_technician);

        spnCantons.setFocusable(false);
        spnCantons.setEnabled(false);
        etPlate.setFocusable(false);
        etPlate.setEnabled(false);
        bCheckPlate.setEnabled(false);
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
        etTechnician.setFocusable(false);
        etTechnician.setEnabled(false);
        etStatus.setFocusable(false);
        etStatus.setEnabled(false);
        etCanton.setFocusable(false);
        etCanton.setEnabled(false);
        spnStatus.setFocusable(false);
        spnStatus.setEnabled(false);
        spnTechnician.setFocusable(false);
        spnTechnician.setEnabled(false);

        bCheckPlate.setOnClickListener(view -> {
            if (matchingCar != null) {
                loadCarInfo(matchingCar);
            }
            else {
                Intent intent = new Intent(RevisionActivity.this, CarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("plate", etPlate.getText().toString());
                intent.putExtra("start", etDateStart.getText().toString());
                intent.putExtra("end", etDateEnd.getText().toString());
                intent.putExtra("cantonPosition", spnCantons.getSelectedItemPosition());
                intent.putExtra("statusPosition", spnStatus.getSelectedItemPosition());
                intent.putExtra("technicianPosition", spnTechnician.getSelectedItemPosition());
                startActivity(intent);
                finish();
            }
        });
        etPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                searchForMatchingCar();
            }
        });
        etTechnician.setOnClickListener(view -> spnTechnician.performClick());
        etStatus.setOnClickListener(view -> spnStatus.performClick());
        etCanton.setOnClickListener(view -> spnCantons.performClick());
        spnCantons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etCanton.setText(adapterCantons.getItem(i).toString());
                searchForMatchingCar();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        spnTechnician.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etTechnician.setText(adapterTechnician.getItem(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etStatus.setText(getString(adapterStatus.getItem(i).getStringResourceId()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        adapterCantons = new CantonListAdapter(this, R.layout.tv_list_view, new ArrayList<>());
        spnCantons.setAdapter(adapterCantons);
        adapterStatus = new StatusListAdapter(this, R.layout.tv_list_view, Status.getAllStatus());
        spnStatus.setAdapter(adapterStatus);
        spnStatus.setSelection(getIntent().getIntExtra("statusPosition", 0));
        adapterTechnician = new ListAdapter<>(this, R.layout.tv_list_view, new ArrayList<>());
        spnTechnician.setAdapter(adapterTechnician);
    }

    @Override
    protected void updateContent() {
        if (revision != null) {
            etPlate.setText(StringUtility.plateWithoutAbbreviation(revision.completeCar.car.getPlate()));
            loadCarInfo(revision.completeCar);
            etDateStart.setText(StringUtility.dateToDateTimeString(revision.revision.getStart(), this));
            Date end = revision.revision.getEnd();
            if (end != null) {
                etDateEnd.setText(StringUtility.dateToDateTimeString(end, this));
            }
            String canton = StringUtility.abbreviationFromPlate(revision.completeCar.car.getPlate());
            spnCantons.post(() -> spnCantons.setSelection(adapterCantons.getPosition(new CantonEntity(canton, canton))));
            spnStatus.post(() -> spnStatus.setSelection(adapterStatus.getPosition(revision.revision.getStatus())));
            spnTechnician.post(() -> spnTechnician.setSelection(adapterTechnician.getPosition(revision.technician)));
        }
    }

    @Override
    protected boolean hasChanges() {
        boolean rv;
        if (revision != null) {
            boolean end;
            if (revision.revision.getEnd() != null) {
                end = !etDateEnd.getText().toString().equals(StringUtility.dateToDateTimeString(revision.revision.getEnd(), this));
            } else {
                end = !etDateEnd.getText().toString().trim().equals("");
            }
            boolean match;
            if (matchingCar != null) {
                match = matchingCar.car.getId() != revision.completeCar.car.getId();
            } else {
                match = true;
            }
            rv = match || end
                    ||spnStatus.getSelectedItem() != revision.revision.getStatus()
                    ||!spnTechnician.getSelectedItem().equals(revision.technician)
                    ||!etDateStart.getText().toString().equals(StringUtility.dateToDateTimeString(revision.revision.getStart(), this));
        } else {
            rv = spnCantons.getSelectedItem() != null
                    || spnStatus.getSelectedItem() != null
                    || spnTechnician.getSelectedItem() != null
                    || !etPlate.getText().toString().trim().equals("")
                    || !etDateStart.getText().toString().trim().equals("")
                    || !etDateEnd.getText().toString().trim().equals("");
        }
        return rv;
    }

    @Override
    protected void deleteItem() {
        if (revision != null) {
            revisionVM.deleteRevision(revision.revision, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Revision " + revision.revision.getId() + " successfully deleted");
                    startListActivity(R.string.revision_delete_success);
                }
                @Override
                public void onFailure(Exception e) {
                    Log.w(TAG, "Failed to delete the revision " + revision.revision.getId());
                    showSnack(R.string.revision_delete_fail);
                }
            });
        }
    }

    /**
     * Searches for an existing car matching the plate entered by the user
     */
    private void searchForMatchingCar() {
        if (cars != null) {
            for (CompleteCar cc : cars) {
                String abbr = ((CantonEntity)spnCantons.getSelectedItem()).getAbbreviation();
                if (cc.car.getPlate().equals(abbr + etPlate.getText().toString())) {
                    matchingCar = cc;
                    bCheckPlate.setImageResource(R.drawable.ic_done_white_24dp);
                    return;
                }
            }
            matchingCar = null;
            bCheckPlate.setImageResource(R.drawable.ic_action_add);
        }
    }

    /**
     * Loads the car information to the interface
     * @param car Car to load
     */
    private void loadCarInfo(CompleteCar car) {
        etBrand.setText(car.modelWithBrand.brand.getBrand());
        etModel.setText(car.modelWithBrand.model.getModel());
        etMileage.setText(StringUtility.intToString(car.car.getKilometers(), this));
        etYear.setText(StringUtility.dateToYearString(car.car.getYear(), this));
    }
}
