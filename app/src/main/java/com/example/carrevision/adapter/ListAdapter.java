package com.example.carrevision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carrevision.R;

import java.util.List;

/**
 * List adapter class for each spinner used in the application
 * @param <T> List object's type
 */
public class ListAdapter<T> extends ArrayAdapter<T> {
    private int resource;
    protected List<T> data;

    /**
     * Constructor of the list adapter class
     * @param context Context
     * @param resource Resource
     * @param data Data list
     */
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data) {
        super(context, resource, data);
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * Gets the item at the given position
     * @param position Item's position
     * @return Item at the given position
     */
    public T getItem(int position) {
        return this.data.get(position);
    }

    /**
     * Returns the item's position
     * @param item Item to search
     * @return Item index
     */
    public int getPosition(T item) {
        if (this.data.contains(item)) {
            for (int i = 0; i < this.data.size(); i++) {
                if (this.data.get(i).equals(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Gets the custom view containing the item
     * @param position View position
     * @param convertView Item position
     * @param parent Parent view group
     * @return Custom view
     */
    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(this.resource, parent, false);
            viewHolder = new ListAdapter.ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.id.tv_list_view);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ListAdapter.ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        if (item != null) {
            viewHolder.itemView.setText(getItemString(item));
        }
        return convertView;
    }

    /**
     * Gets the string representation of the item
     * @param item Item
     * @return String representation
     */
    protected String getItemString(T item) {
        return item.toString();
    }

    /**
     * Updates the data list
     * @param data New data list
     */
    public void updateData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * Inner class view holder for the item
     */
    private static class ViewHolder {
        TextView itemView;
    }
}
