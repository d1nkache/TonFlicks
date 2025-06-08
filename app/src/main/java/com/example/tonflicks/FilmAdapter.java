package com.example.tonflicks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Film> items;

    public FilmAdapter(List<Film> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.ViewHolder holder, int position) {
        Film elem = items.get(position);
        holder.ivPoster.setImageResource(elem.getImageResId());
        holder.tvGenre.setText(elem.getGenre());
        holder.tvTitle.setText(elem.getTitle());

        holder.itemView.setOnClickListener(v -> {
            FragmentManager fm = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            FilmDetailsDialog dialog = new FilmDetailsDialog(elem);
            dialog.show(fm, "film_details");
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle  = itemView.findViewById(R.id.tvTitle);
            tvGenre  = itemView.findViewById(R.id.tvGenre);
        }
    }
}
