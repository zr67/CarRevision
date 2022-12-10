package com.example.carrevision.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.carrevision.BaseApp;
import com.example.carrevision.R;
import com.example.carrevision.database.repository.TechnicianRepository;
import com.example.carrevision.ui.car.CarsActivity;
import com.example.carrevision.ui.management.LoginActivity;
import com.example.carrevision.ui.management.SettingsActivity;
import com.example.carrevision.ui.revision.RevisionsActivity;
import com.example.carrevision.util.LocaleManager;
import com.example.carrevision.viewmodel.technician.TechnicianVM;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity base class for all application activities
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS_NAME;
    public static final String PREFS_UNAME;
    public static final String PREFS_PWD;
    protected FrameLayout frameLayout;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected static int position = R.id.nav_revisions;
    protected static boolean TECHNICIAN_CONNECTED;
    protected static boolean ADMIN_CONNECTED;
    protected static boolean INITIALIZED;

    static {
        PREFS_NAME = "SharedPrefs";
        PREFS_UNAME = "Username";
        PREFS_PWD = "Password";
        TECHNICIAN_CONNECTED = false;
        ADMIN_CONNECTED = false;
        INITIALIZED = false;
    }

    /**
     * Gets the current position, used when the language has been changed to restore the correct activity
     * @return Current position
     */
    public static int getPosition() {
        return position;
    }

    /**
     * Shows a message to the user in a snack bar
     * @param text Text to display
     */
    protected void showSnack(String text) {
        Snackbar.make(BaseActivity.this, frameLayout, text, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Shows a message to the user in a snack bar
     * @param resource Text's resource identifier
     */
    protected void showSnack(int resource) {
        showSnack(getString(resource));
    }

    /**
     * Updates the navigation menu with the correct options
     */
    protected void updateNavMenu() {
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(!TECHNICIAN_CONNECTED);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(TECHNICIAN_CONNECTED);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        TECHNICIAN_CONNECTED = false;
        ADMIN_CONNECTED = false;
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        if (prefs.contains(PREFS_UNAME)) {
            prefs.edit().remove(PREFS_UNAME).apply();
        }
        if (prefs.contains(PREFS_PWD)) {
            prefs.edit().remove(PREFS_PWD).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleManager(this).applyLanguage();

        if (!INITIALIZED) {
            INITIALIZED = true;
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            if (prefs.contains(PREFS_UNAME) && prefs.contains(PREFS_PWD)) {
                TechnicianRepository repo = ((BaseApp)getApplication()).getTechnicianRepository();
                repo.login(prefs.getString(PREFS_UNAME, ""), prefs.getString(PREFS_PWD, ""), task -> {
                    if (task.isSuccessful()) {
                        TechnicianVM.Factory fact = new TechnicianVM.Factory(getApplication(), FirebaseAuth.getInstance().getUid());
                        TechnicianVM technicianVM = new ViewModelProvider(new ViewModelStore(), fact).get(TechnicianVM.class);
                        technicianVM.getTechnician().observe(this, technicianEntity -> {
                            if (technicianEntity != null) {
                                TECHNICIAN_CONNECTED = true;
                                ADMIN_CONNECTED = technicianEntity.getAdmin();
                                Intent intent = new Intent(BaseActivity.this, RevisionsActivity.class);
                                intent.putExtra("snackMsg", String.format(getString(R.string.welcome_back_msg), technicianEntity.getFirstname()));
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }
        }

        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = findViewById(R.id.frameLayoutContent);
        drawerLayout = findViewById(R.id.base_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.base_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateNavMenu();

        if (getIntent().hasExtra("snackMsg")) {
            showSnack(getIntent().getStringExtra("snackMsg"));
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        BaseActivity.position = 0;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        Intent intent = null;
        if (id != R.id.nav_login) {
            finish();
        }
        if (id == R.id.nav_revisions) {
            intent = new Intent(this, RevisionsActivity.class);
        } else if (id == R.id.nav_cars) {
            intent = new Intent(this, CarsActivity.class);
        } else if (id == R.id.nav_login) {
            intent = new Intent(this, LoginActivity.class);
        } else if (id == R.id.nav_logout) {
            logout();
            updateNavMenu();
            if (position == R.id.nav_cars) {
                intent = new Intent(this, CarsActivity.class);
            } else {
                intent = new Intent(this, RevisionsActivity.class);
            }
            intent.putExtra("snackMsg", getString(R.string.logout_msg));
        }
        if (intent != null) {
            startActivity(intent);
        }
        if (id != R.id.nav_logout) {
            BaseActivity.position = id;
            navigationView.setCheckedItem(id);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
