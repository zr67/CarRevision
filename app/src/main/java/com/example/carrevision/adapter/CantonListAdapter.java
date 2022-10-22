package com.example.carrevision.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.carrevision.database.entity.CantonEntity;

import java.util.List;
import java.util.Locale;

public class CantonListAdapter extends ListAdapter<CantonEntity> {
    /**
     * Constructor of the list adapter class
     * @param context Context
     * @param resource Resource
     * @param data Data list
     */
    public CantonListAdapter(@NonNull Context context, int resource, @NonNull List<CantonEntity> data) {
        super(context, resource, data);
    }

    /**
     * Gets the canton position by it's abbreviation
     * @param item Item to search
     * @return Canton index
     */
    @Override
    public int getPosition(CantonEntity item) {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getAbbreviation().toUpperCase(Locale.ROOT).equals(item.getAbbreviation().toUpperCase(Locale.ROOT))) {
                return i;
            }
        }
        return -1;
    }
}
