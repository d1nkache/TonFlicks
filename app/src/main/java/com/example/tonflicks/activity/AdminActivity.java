package com.example.tonflicks.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tonflicks.R;
import com.example.tonflicks.client.AdminApi;
import com.example.tonflicks.client.FilmRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActivity extends AppCompatActivity {

    private AdminApi adminApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminApi = retrofit.create(AdminApi.class);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etGenre = findViewById(R.id.etGenre);
        EditText etDescription = findViewById(R.id.etDescription);
        EditText etImageResId = findViewById(R.id.etImageResId);
        EditText etRating = findViewById(R.id.etRating);
        EditText etYear = findViewById(R.id.etYear);
        EditText etReleaseDate = findViewById(R.id.etReleaseDate);
        EditText etCinemaId = findViewById(R.id.etCinemaId);
        EditText etUserId = findViewById(R.id.etUserId);

        Button btnAddFilm = findViewById(R.id.btnAddFilm);
        Button btnGrantAdmin = findViewById(R.id.btnGrantAdmin);

        btnAddFilm.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String genre = etGenre.getText().toString();
            String description = etDescription.getText().toString();
            String imageResId = etImageResId.getText().toString();
            float rating;
            int year;
            String releaseDate = etReleaseDate.getText().toString();
            int cinemaId;

            try {
                rating = Float.parseFloat(etRating.getText().toString());
                year = Integer.parseInt(etYear.getText().toString());
                cinemaId = Integer.parseInt(etCinemaId.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Некорректные числовые значения", Toast.LENGTH_SHORT).show();
                return;
            }

            if (title.isEmpty() || genre.isEmpty() || description.isEmpty() || releaseDate.isEmpty()) {
                Toast.makeText(this, "Заполните все обязательные поля", Toast.LENGTH_SHORT).show();
                return;
            }

            FilmRequest request = new FilmRequest(title, genre, description, imageResId,
                    rating, year, releaseDate, cinemaId);

            adminApi.addFilm(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AdminActivity.this, "Фильм добавлен", Toast.LENGTH_SHORT).show();
                        clearFilmFields();
                    } else {
                        Toast.makeText(AdminActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AdminActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("AdminActivity", "Ошибка добавления фильма", t);
                }
            });
        });

        btnGrantAdmin.setOnClickListener(v -> {
            String userIdStr = etUserId.getText().toString();

            if (userIdStr.isEmpty()) {
                Toast.makeText(this, "Введите ID пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long userId = Long.parseLong(userIdStr);

                adminApi.grantAdmin(userId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AdminActivity.this, "Права администратора выданы", Toast.LENGTH_SHORT).show();
                            etUserId.setText("");
                        } else {
                            Toast.makeText(AdminActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("AdminActivity", "Ошибка выдачи админки", t);
                    }
                });
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Некорректный ID пользователя", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFilmFields() {
        ((EditText) findViewById(R.id.etTitle)).setText("");
        ((EditText) findViewById(R.id.etGenre)).setText("");
        ((EditText) findViewById(R.id.etDescription)).setText("");
        ((EditText) findViewById(R.id.etImageResId)).setText("");
        ((EditText) findViewById(R.id.etRating)).setText("");
        ((EditText) findViewById(R.id.etYear)).setText("");
        ((EditText) findViewById(R.id.etReleaseDate)).setText("");
        ((EditText) findViewById(R.id.etCinemaId)).setText("");
    }
}