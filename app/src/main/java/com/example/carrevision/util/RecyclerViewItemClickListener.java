package com.example.carrevision.util;

import android.view.View;

/**
 * Interface used to communicate when a recycler view item is clicked
 */
public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);
}
