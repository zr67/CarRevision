package com.example.carrevision.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.carrevision.R;

/**
 * Base activity class for the details, creation and update of a single entity
 */
public abstract class SingleObjectActivity extends BaseActivity {
    protected boolean editable, ignoreChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editable = false;
        ignoreChanges = false;
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
        }
        else {
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
        return dlg;
    }

    /**
     * Gets the unsaved changes dialog
     * @return Base dialog without buttons
     */
    private AlertDialog getUnsavedChangesDlg() {
        return new AlertDialog.Builder(SingleObjectActivity.this)
                .setTitle(R.string.unsaved_changes)
                .setCancelable(false)
                .setMessage(R.string.unsaved_changes_msg)
                .create();
    }

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