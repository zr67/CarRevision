package com.example.carrevision.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrevision.R;
import com.example.carrevision.database.pojo.CompleteCar;
import com.example.carrevision.util.RecyclerViewItemClickListener;

import java.util.List;

/**
 * Adapter class for the cars recycle view
 */
public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder> {
    private List<CompleteCar> data;
    private final RecyclerViewItemClickListener listener;

    /**
     * Adapter class fot the cars recycle view constructor
     * @param listener List's items listener
     */
    public CarRecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.car_recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(holder.getAdapterPosition()));
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompleteCar car = data.get(position);
        holder.tvPlate.setText(car.car.getPlate());
        holder.tvModel.setText(car.modelWithBrand.model.getModel());
        holder.tvBrand.setText(car.modelWithBrand.brand.getBrand());
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
    public void setData(final List<CompleteCar> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, this.data.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CarRecyclerAdapter.this.data.size();
                }
                @Override
                public int getNewListSize() {
                    return data.size();
                }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CarRecyclerAdapter.this.data.get(oldItemPosition).car.getId().equals(data.get(newItemPosition).car.getId());
                }
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return CarRecyclerAdapter.this.data.get(oldItemPosition).equals(data.get(newItemPosition));
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
        private final TextView tvBrand;
        private final TextView tvModel;

        /**
         * Inner class constructor for the cars list
         * @param itemView Item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvPlate = itemView.findViewById(R.id.tv_plate);
            this.tvBrand = itemView.findViewById(R.id.tv_datetime);
            this.tvModel = itemView.findViewById(R.id.tv_status);
        }
    }
}
