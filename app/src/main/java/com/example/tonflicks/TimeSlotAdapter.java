package com.example.tonflicks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {
    private List<TimeSlot> items;
    private int selectedPosition = RecyclerView.NO_POSITION;
    public TimeSlotAdapter(List<TimeSlot> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeSlot elem = items.get(position);
        holder.tvTime.setText(elem.getTime());

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081"));
            holder.tvTime.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvTime.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);
        });
    }


    @Override
    public int getItemCount() {
        return this.items.size();
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

