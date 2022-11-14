package com.example.carrevision.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.carrevision.util.Status;

import java.util.List;

/**
 * Status list adapter class
 */
public class StatusListAdapter extends ListAdapter<Status> {
    /**
     * Constructor of the status list adapter class
     * @param context Context
     * @param resource Resource
     * @param data Data list
     */
    public StatusListAdapter(@NonNull Context context, int resource, @NonNull List<Status> data) {
        super(context, resource, data);
    }
    @Override
    protected String getItemString(Status item) {
        return getContext().getString(item.getStringResourceId());
    }
}
