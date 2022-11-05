package com.example.carrevision.ui.car;

import com.example.carrevision.ui.SingleObjectActivity;

/**
 * Car activity class
 */
public class CarActivity extends SingleObjectActivity {

    @Override
    protected boolean saveChanges() {
        return false;
    }

    @Override
    protected boolean hasChanges() {
        return false;
    }

    @Override
    protected void deleteItem() {

    }
}
