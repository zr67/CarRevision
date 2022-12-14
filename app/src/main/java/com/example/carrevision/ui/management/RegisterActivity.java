package com.example.carrevision.ui.management;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carrevision.BaseApp;
import com.example.carrevision.R;
import com.example.carrevision.adapter.ListAdapter;
import com.example.carrevision.database.async.technician.CreateTechnician;
import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.ui.revision.RevisionsActivity;
import com.example.carrevision.util.OnAsyncEventListener;

import java.util.Arrays;

/**
 * Register activity class
 */
public class RegisterActivity extends BaseActivity {
    private final String TAG = "RegisterActivity";
    private Spinner spnTitle;
    private EditText etFirstname, etLastname, etEmail, etPassword1, etPassword2, etTitle;
    private SwitchCompat swRememberMe;
    private TechnicianRepository repository;
    private ListAdapter<String> adapterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_register, frameLayout);
        setTitle(R.string.title_activity_register);
        repository = ((BaseApp) getApplication()).getTechnicianRepository();
        adapterTitle = new ListAdapter<>(this, R.layout.tv_list_view, Arrays.asList(getResources().getStringArray(R.array.titles)));
        spnTitle = findViewById(R.id.spn_register_title);
        spnTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etTitle.setText(adapterTitle.getItem(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        etTitle = findViewById(R.id.et_register_title);
        etTitle.setOnClickListener(view -> spnTitle.performClick());
        etFirstname = findViewById(R.id.et_register_firstname);
        etLastname = findViewById(R.id.et_register_lastname);
        etEmail = findViewById(R.id.et_register_email);
        etPassword1 = findViewById(R.id.et_register_password1);
        etPassword2 = findViewById(R.id.et_register_password2);
        swRememberMe = findViewById(R.id.sw_register_remember);
        Button bRegister = findViewById(R.id.b_register_register);
        bRegister.setOnClickListener(view -> createTech((String) spnTitle.getSelectedItem(), etFirstname.getText().toString(), etLastname.getText().toString(), etEmail.getText().toString(), etPassword1.getText().toString(), etPassword2.getText().toString()));
        spnTitle.setAdapter(adapterTitle);
    }

    /**
     * Creates a new technician
     * @param title Technician's title
     * @param firstname Technician's firstname
     * @param lastname Technician's lastname
     * @param email Technician's email
     * @param pass1 Technician's password
     * @param pass2 Technician's password confirmation
     */
    private void createTech(String title, String firstname, String lastname, String email, String pass1, String pass2) {
        etEmail.setError(null);
        etFirstname.setError(null);
        etLastname.setError(null);
        etPassword2.setError(null);
        etPassword1.setError(null);
        if (TextUtils.isEmpty(pass1)) {
            etPassword1.setError(getString(R.string.err_password_empty));
            etPassword1.requestFocus();
            etPassword2.setText("");
            return;
        }
        if (TextUtils.isEmpty(firstname)) {
            etFirstname.setError(getString(R.string.err_firstname_empty));
            etFirstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            etLastname.setError(getString(R.string.err_lastname_empty));
            etLastname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.err_email_empty));
            etEmail.requestFocus();
            return;
        }
        if (!pass1.equals(pass2)) {
            etPassword2.setError(getString(R.string.err_password_mismatch));
            etPassword2.requestFocus();
            etPassword2.setText("");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.err_email_invalid));
            etEmail.requestFocus();
            return;
        }
        TechnicianEntity technician = new TechnicianEntity(title, firstname, lastname, email, pass1, false);
        new CreateTechnician((BaseApp) getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "User created with mail " + technician.getEmail());
                setResponse(true);
            }
            @Override
            public void onFailure(Exception e) {
                Log.w(TAG, "Failed to create user with mail " + technician.getEmail());
                setResponse(false);
            }
        }).execute(technician);
    }

    /**
     * Logs in the technician if the creation is successful, displays an error otherwise
     * @param response Creation's result
     */
    private void setResponse(boolean response) {
        if (response) {
            repository.getTechnician(getApplication(), etEmail.getText().toString()).observe(RegisterActivity.this, technicianEntity -> {
                if (technicianEntity != null) {
                    ((BaseApp) getApplication()).getAccountManager().login(technicianEntity.getId(), technicianEntity.isAdmin(), swRememberMe.isChecked());
                    Intent intent = new Intent(RegisterActivity.this, RevisionsActivity.class);
                    intent.putExtra("snackMsg", String.format(getString(R.string.welcome_msg), technicianEntity.getFirstname()));
                    updateNavMenu();
                    finish();
                    startActivity(intent);
                }
            });
        }
        else {
            etEmail.setError(getString(R.string.err_email_inuse));
            etEmail.requestFocus();
        }
    }
}
