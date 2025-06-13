package com.example.tonflicks.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tonflicks.client.FilmApi;
import com.example.tonflicks.client.FilmResponse;
import com.example.tonflicks.fragment.FilmCategoryFragment;
import com.example.tonflicks.fragment.NowPlayingFragment;
import com.example.tonflicks.R;
import com.example.tonflicks.recyclerView.Film;
import com.example.tonflicks.recyclerView.FilmAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements FilmCategoryFragment.OnCategorySelectedListener {

    int userId;
    private static final int INTERNET_PERMISSION_REQUEST_CODE = 1;
    private NowPlayingFragment nowPlayingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        checkInternetPermission();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        nowPlayingFragment = NowPlayingFragment.newInstance("Москва", "2025", userId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nowPlayingFragmentContainer, nowPlayingFragment)
                .commit();


        FilmCategoryFragment filmCategoryFragment = FilmCategoryFragment.newInstance("категории", "2025");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.categoriesFragmentContainer, filmCategoryFragment)
                .commit();


        ImageButton busketImageButton = findViewById(R.id.basketButton);
        busketImageButton.setOnClickListener(v -> {
            if (userId == -1) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }

            if (userId == 331) {
                Toast.makeText(MainActivity.this, "у вас Гостевой режим!", Toast.LENGTH_SHORT).show();
            }

            if (userId != -1 && userId != 331) {
                Toast.makeText(MainActivity.this, "Корзина открыта", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
            }
        });

        EditText searchInput = findViewById(R.id.searchMovies);

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                String query = searchInput.getText().toString().trim();
                if (!query.isEmpty()) {
                    searchFilms(query);
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCategorySelected(String category) {
        if (nowPlayingFragment != null) {
            nowPlayingFragment.updateCategory(category);
        }
    }

    private void checkInternetPermission() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            return;
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    INTERNET_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Разрешение на интернет доступно", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INTERNET_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение на интернет предоставлено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Разрешение на интернет отклонено. Функциональность ограничена.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private List<Film> convertToFilmList(List<FilmResponse> filmResponses) {
        List<Film> films = new ArrayList<>();

        if (filmResponses != null) {
            for (FilmResponse response : filmResponses) {
                films.add(new Film(
                        response.title,
                        response.genre,
                        response.description,
                        response.imageResId,
                        response.rating,
                        response.year
                ));
            }
        }

        return films;
    }
    private void searchFilms(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FilmApi filmApi = retrofit.create(FilmApi.class);

        filmApi.getFilmsByTitle(query).enqueue(new Callback<List<FilmResponse>>() {
            @Override
            public void onResponse(Call<List<FilmResponse>> call, Response<List<FilmResponse>> response) {
                if (response.isSuccessful()) {
                    List<Film> films = convertToFilmList(response.body());
                    nowPlayingFragment.updateFilms(films);

                    if (films.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Фильмы не найдены", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Найдено фильмов: " + films.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка ответа сервера: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка запроса: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}