package com.example.tonflicks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FilmDetailsDialog extends DialogFragment {

    private Film film;

    public FilmDetailsDialog(Film film) {
        this.film = film;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_film_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView title = view.findViewById(R.id.tvTitle);
        TextView genre = view.findViewById(R.id.tvGenre);
        TextView description = view.findViewById(R.id.tvDescription);
        ImageView poster = view.findViewById(R.id.ivPoster);
        Button buyButton = view.findViewById(R.id.btnBuy);

        title.setText(film.getTitle());
        genre.setText(film.getGenre());
        description.setText("Я НИКОГДА НЕ ДРОЧИЛ НА ГЕЙСКУЮ ПОРНУХУ"); // еблан
        poster.setImageResource(film.getImageResId());

        buyButton.setOnClickListener(v ->
        {
            Toast.makeText(getContext(), "Билет куплен!", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
