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
    private OnCategoryClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // Track the selected position

    // Интерфейс для обработки кликов по категории
    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }

    public CategoryAdapter(List<Category> items) {
        this.items = items;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = items.get(position);
        holder.tvCategory.setText(category.getName());

        // Обновление фона и цвета текста в зависимости от состояния
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081")); // Выбранный цвет
            holder.tvCategory.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE); // Не выбранный цвет
            holder.tvCategory.setTextColor(Color.BLACK);
        }

        // Обработчик кликов
        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                // Сохраняем предыдущую позицию
                int previousSelectedPosition = selectedPosition;

                // Сбрасываем выбор предыдущей категории
                if (previousSelectedPosition != RecyclerView.NO_POSITION) {
                    items.get(previousSelectedPosition).setSelected(false);
                    notifyItemChanged(previousSelectedPosition); // Обновляем только предыдущий элемент
                }

                // Устанавливаем выбор для текущей категории
                selectedPosition = currentPosition;
                items.get(currentPosition).setSelected(true);
                notifyItemChanged(currentPosition); // Обновляем только текущий элемент

                // Уведомляем слушателя о выбранной категории
                if (listener != null) {
                    listener.onCategoryClick(category.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items != null ? this.items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}