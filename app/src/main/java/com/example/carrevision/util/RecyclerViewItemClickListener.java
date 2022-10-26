package com.example.carrevision.util;

import android.view.View;

/**
 * Interface used to communicate when a recycler view item is clicked
 */
public interface RecyclerViewItemClickListener {
    /**
     * Defines the event when a recycler view item is clicked
     * @param v View clicked
     * @param position Position clicked
     */
    void onItemClick(View v, int position);
}
