package com.example.carrevision.util;

/**
 * Interface used to communicate when a recycler view item is clicked
 */
public interface RecyclerViewItemClickListener {
    /**
     * Defines the event when a recycler view item is clicked
     * @param position Position clicked
     */
    void onItemClick(int position);
}
