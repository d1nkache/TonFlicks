package com.example.tonflicks.recyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tonflicks.R;

import java.util.List;

public class DateSlotAdapter extends RecyclerView.Adapter<DateSlotAdapter.ViewHolder> {

    private List<DateSlot> items;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public DateSlotAdapter(List<DateSlot> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DateSlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_slot_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateSlotAdapter.ViewHolder holder, int position) {
        DateSlot slot = items.get(position);
        holder.tvTime.setText(slot.getLabel());

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081"));
            holder.tvTime.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvTime.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(v -> {
            int previous = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previous);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Boolean isSelected;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
