package com.example.carrevision.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrevision.R;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.RecyclerViewItemClickListener;
import com.example.carrevision.util.StringUtility;

import java.util.List;

/**
 * Adapter class for the revisions recycle view
 */
public class RevisionRecyclerAdapter extends RecyclerView.Adapter<RevisionRecyclerAdapter.ViewHolder> {
    private List<CompleteRevision> data;
    private final RecyclerViewItemClickListener listener;
    private final BaseActivity activity;

    /**
     * Adapter class fot the revisions recycle view constructor
     * @param listener List's items listener
     */
    public RevisionRecyclerAdapter(RecyclerViewItemClickListener listener, BaseActivity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.revision_recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompleteRevision revision = data.get(position);
        holder.tvPlate.setText(revision.completeCar.car.getPlate());
        holder.tvDatetime.setText(StringUtility.dateToDateTimeString(revision.revision.getStart(), activity));
        holder.tvStatus.setText(activity.getString(revision.revision.getStatus().getStringResourceId()));
    }

    @Override
    public int getItemCount() {
        if (this.data != null) {
            return this.data.size();
        }
        else {
            return 0;
        }
    }

    /**
     * Sets the adapter data list
     * @param data New data list
     */
    public void setData(final List<CompleteRevision> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, this.data.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RevisionRecyclerAdapter.this.data.size();
                }
                @Override
                public int getNewListSize() {
                    return data.size();
                }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RevisionRecyclerAdapter.this.data.get(oldItemPosition).revision.getId() == data.get(newItemPosition).revision.getId();
                }
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return RevisionRecyclerAdapter.this.data.get(oldItemPosition).equals(data.get(newItemPosition));
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * Inner class view holder for the cars list
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlate;
        private final TextView tvDatetime;
        private final TextView tvStatus;

        /**
         * Inner class constructor for the revisions list
         * @param itemView Item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvPlate = itemView.findViewById(R.id.tv_plate);
            this.tvDatetime = itemView.findViewById(R.id.tv_datetime);
            this.tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}



