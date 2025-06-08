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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> items;

    public CategoryAdapter(List<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category elem = items.get(position);
        holder.tvCategory.setText(elem.getName());

        if (elem.isSelected()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081"));
            holder.tvCategory.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvCategory.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = holder.getAdapterPosition();
            boolean newState = !elem.isSelected();
            elem.setSelected(newState);
            items.remove(oldPosition);
            int newPosition = newState ? 0 : items.size();
            items.add(newPosition, elem);
            notifyItemMoved(oldPosition, newPosition);
            notifyItemChanged(newPosition);
        });
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Boolean isSelected;
        TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
