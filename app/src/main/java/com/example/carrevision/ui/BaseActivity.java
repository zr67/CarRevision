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

import com.example.carrevision.R;
import com.example.carrevision.ui.car.CarsActivity;
import com.example.carrevision.ui.management.LoginActivity;
import com.example.carrevision.ui.management.SettingsActivity;
import com.example.carrevision.ui.revision.RevisionsActivity;
import com.example.carrevision.util.LocaleManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Activity base class for all application activities
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedTech";
    public static final String PREFS_ADMIN = "AdminPrivileges";
    protected FrameLayout frameLayout;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected static int position;

    /**
     * Gets if the technician is connected
     * @return True if connected, false otherwise
     */
    protected boolean technicianIsConnected() {
        return getConnectedTechnicianId() >= 0;
    }

    /**
     * Gets the connected technician identifier
     * @return Connected technician's identifier, -1 if no technician is connected
     */
    protected int getConnectedTechnicianId() {
        return getSharedPreferences(PREFS_NAME, 0).getInt(PREFS_USER, -1);
    }

    /**
     * Gets if the connected technician is an administrator
     * @return True if administrator, false otherwise
     */
    protected boolean technicianIsAdmin() {
        return getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_ADMIN, false);
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
        boolean loggedIn = technicianIsConnected();
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(!loggedIn);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(loggedIn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleManager(this).applyLanguage();

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
        if (id != R.id.nav_login && id != R.id.nav_logout) {
            BaseActivity.position = id;
            navigationView.setCheckedItem(id);
        }

        Intent intent = null;
        if (id == R.id.nav_revisions) {
            intent = new Intent(this, RevisionsActivity.class);
        } else if (id == R.id.nav_cars) {
            intent = new Intent(this, CarsActivity.class);
        } else if (id == R.id.nav_login) {
            intent = new Intent(this, LoginActivity.class);
        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
            editor.remove(PREFS_USER);
            editor.remove(PREFS_ADMIN);
            editor.apply();
            updateNavMenu();
            if (position == R.id.nav_revisions) {
                intent = new Intent(this, RevisionsActivity.class);
            } else if (position == R.id.nav_cars) {
                intent = new Intent(this, CarsActivity.class);
            }
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
