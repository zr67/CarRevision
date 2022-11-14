package com.example.carrevision.ui;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;

import com.example.carrevision.R;

/**
 * Base activity class for the details, creation and update of a single entity
 */
public abstract class SingleObjectActivity extends BaseActivity {
    protected boolean editable, ignoreChanges;
    protected Drawable dArrow, dArrowDisabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editable = false;
        ignoreChanges = false;
        dArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.arrow_down, null);
        dArrowDisabled = ResourcesCompat.getDrawable(getResources(), R.drawable.arrow_down_disabled, null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean rv = false;
        if (hasChanges() && !ignoreChanges) {
            drawerLayout.closeDrawer(GravityCompat.START);
            AlertDialog dlg = getUnsavedChangesDlg();
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_discard), (dialogInterface, i) -> {
                ignoreChanges = true;
                onNavigationItemSelected(item);
            });
            dlg.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_save), (dialogInterface, i) -> {
                saveChanges();
                onNavigationItemSelected(item);
            });
            dlg.show();
        }
        else {
            rv = super.onNavigationItemSelected(item);
        }
        return rv;
    }

    @Override
    public void onBackPressed() {
        if (hasChanges() && !ignoreChanges) {
            AlertDialog dlg = getUnsavedChangesDlg();
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_discard), (dialogInterface, i) -> {
                ignoreChanges = true;
                onBackPressed();
            });
            dlg.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_save), (dialogInterface, i) -> {
                saveChanges();
                onBackPressed();
            });
            dlg.show();
        } else if (editable) {
            switchMode();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean rv = false;
        if (hasChanges() && !ignoreChanges) {
            AlertDialog dlg = getUnsavedChangesDlg();
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_discard), (dialogInterface, i) -> {
                ignoreChanges = true;
                onOptionsItemSelected(item);
            });
            dlg.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_save), (dialogInterface, i) -> {
                saveChanges();
                onOptionsItemSelected(item);
            });
            dlg.show();
        }
        else {
            rv = super.onOptionsItemSelected(item);
        }
        return rv;
    }

    /**
     * Gets the confirm delete dialog
     * @return Delete dialog
     */
    protected AlertDialog getDeleteConfirmationDlg() {
        final AlertDialog dlg = new AlertDialog.Builder(SingleObjectActivity.this)
                .setTitle(R.string.action_delete)
                .setCancelable(false)
                .setMessage(R.string.action_delete_msg)
                .create();
        dlg.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialogInterface, i) -> dlg.dismiss());
        dlg.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialogInterface, i) -> {
            dlg.dismiss();
            deleteItem();
        });
        dlg.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                dlg.dismiss();
            }
            return true;
        });
        return dlg;
    }

    /**
     * Gets the unsaved changes dialog
     * @return Base dialog without buttons
     */
    private AlertDialog getUnsavedChangesDlg() {
        final AlertDialog dlg = new AlertDialog.Builder(SingleObjectActivity.this)
                .setTitle(R.string.unsaved_changes)
                .setCancelable(false)
                .setMessage(R.string.unsaved_changes_msg)
                .create();
        dlg.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                dlg.dismiss();
            }
            return true;
        });
        return dlg;
    }

    /**
     * Initializes the view
     */
    protected abstract void initView();

    /**
     * Updates the content with the current data
     */
    protected abstract void updateContent();

    /**
     * Starts the list activity
     * @param resource Resource identifier for the message to display
     */
    protected abstract void startListActivity(int resource);

    /**
     * Sets up all the remaining view-models
     */
    protected abstract void setupVMs();

    /**
     * Switches between the edit and the read-only mode
     */
    protected abstract void switchMode();

    /**
     * Saves all changes made to the item by the user
     */
    protected abstract boolean saveChanges();

    /**
     * Detects if the item has changes
     * @return True if the item has changes, false otherwise
     */
    protected abstract boolean hasChanges();

    /**
     * Deletes the current item and redirects back to the list activity
     */
    protected abstract void deleteItem();
}