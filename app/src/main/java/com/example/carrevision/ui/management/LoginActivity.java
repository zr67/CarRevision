package com.example.carrevision.ui.management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.BaseApp;
import com.example.carrevision.R;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.ui.revision.RevisionsActivity;
import com.example.carrevision.viewmodel.technician.TechnicianVM;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Login activity class
 */
public class LoginActivity extends BaseActivity {
    private EditText etEmail, etPassword;
    private SwitchCompat swRememberMe;
    private TechnicianRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_login, frameLayout);
        setTitle(R.string.title_activity_login);
        repository = ((BaseApp) getApplication()).getTechnicianRepository();
        etEmail = findViewById(R.id.et_login_mail);
        etPassword = findViewById(R.id.et_login_password);
        swRememberMe = findViewById(R.id.sw_login_remember);
        Button bLogin = findViewById(R.id.b_login_login);
        bLogin.setOnClickListener(view -> login());
        Button bRegister = findViewById(R.id.b_login_register);
        bRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    /**
     * Tries to proceed the login
     */
    private void login() {
        etEmail.setError(null);
        etPassword.setError(null);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.err_password_empty));
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.err_email_empty));
            etEmail.requestFocus();
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.err_email_invalid));
            etEmail.requestFocus();
            return;
        }
        repository.login(email, password, task -> {
            if (task.isSuccessful()) {
                TechnicianVM.Factory factory = new TechnicianVM.Factory(getApplication(), FirebaseAuth.getInstance().getUid());
                TechnicianVM technicianVM = new ViewModelProvider(new ViewModelStore(), factory).get(TechnicianVM.class);
                technicianVM.getTechnician().observe(this, technicianEntity -> {
                    if (technicianEntity != null) {
                        TECHNICIAN_CONNECTED = true;
                        ADMIN_CONNECTED = technicianEntity.getAdmin();
                        if (swRememberMe.isChecked()) {
                            SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                            editor.putString(BaseActivity.PREFS_UNAME, email);
                            editor.putString(BaseActivity.PREFS_PWD, password);
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, RevisionsActivity.class);
                        intent.putExtra("snackMsg", String.format(getString(R.string.welcome_back_msg), technicianEntity.getFirstname()));
                        updateNavMenu();
                        finish();
                        startActivity(intent);
                        BaseActivity.position = R.id.nav_revisions;
                    }
                });
            } else {
                etEmail.setError(getString(R.string.err_creds_unknown));
                etEmail.requestFocus();
                etPassword.setText("");
            }
        });
    }
}
