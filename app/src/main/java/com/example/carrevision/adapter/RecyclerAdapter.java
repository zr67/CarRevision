package com.example.carrevision.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrevision.R;
import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.util.RecyclerViewItemClickListener;

import java.util.List;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<T> _data;
    private final RecyclerViewItemClickListener _listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView _textView;
        ViewHolder(TextView textView) {
            super(textView);
            _textView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        _listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> { _listener.onItemClick(view, viewHolder.getAdapterPosition()); });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        T item = _data.get(position);
        String text = "";
        if (item.getClass().equals(CarEntity.class)) {
            text = ((CarEntity) item).getPlate();
        }
        else if (item.getClass().equals(RevisionEntity.class)) {
            text = ((RevisionEntity) item).getStatus();
        }
        holder._textView.setText(text);
    }

    @Override
    public int getItemCount() {
        if (_data != null) {
            return _data.size();
        }
        else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (_data == null) {
            _data = data;
            notifyItemRangeInserted(0, data.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return _data.size();
                }
                @Override
                public int getNewListSize() {
                    return data.size();
                }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (_data instanceof CarEntity) {
                        return ((CarEntity) _data.get(oldItemPosition)).getId() == ((CarEntity) data.get(newItemPosition)).getId();
                    }
                    if (_data instanceof RevisionEntity) {
                        return ((RevisionEntity) _data.get(oldItemPosition)).getId() == ((RevisionEntity) data.get(newItemPosition)).getId();
                    }
                    return false;
                }
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (_data instanceof CarEntity) {
                        CarEntity newCar = (CarEntity) data.get(newItemPosition);
                        CarEntity oldCar = (CarEntity) _data.get(newItemPosition);
                        return newCar.equals(oldCar);
                    }
                    if (_data instanceof RevisionEntity) {
                        RevisionEntity newRevision = (RevisionEntity) data.get(newItemPosition);
                        RevisionEntity oldRevision = (RevisionEntity) _data.get(newItemPosition);
                        return newRevision.equals(oldRevision);
                    }
                    return false;
                }
            });
            _data = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
