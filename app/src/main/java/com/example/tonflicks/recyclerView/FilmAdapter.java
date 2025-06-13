package com.example.tonflicks.recyclerView;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tonflicks.activity.LoginActivity;
import com.example.tonflicks.activity.MainActivity;
import com.example.tonflicks.dialog.FilmDetailsDialog;
import com.example.tonflicks.R;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private int userId;
    private List<Film> items;

    public FilmAdapter(List<Film> items, int userId) {
        this.userId = userId;
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
        holder.ivPoster.setImageResource(R.drawable.poster_placeholder);
//        holder.ivPoster.setImageResource(elem.getImageResId());
        holder.tvGenre.setText(elem.getGenre());
        holder.tvTitle.setText(elem.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Log.d("FilmAdapter", "Opening dialog for film: " + elem.getTitle());
            FragmentManager fm = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            FilmDetailsDialog dialog = new FilmDetailsDialog(elem, userId);
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
