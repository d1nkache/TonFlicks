package com.example.tonflicks.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import com.example.tonflicks.fragment.FilmCategoryFragment;
import com.example.tonflicks.fragment.NowPlayingFragment;
import com.example.tonflicks.R;

public class MainActivity extends AppCompatActivity implements FilmCategoryFragment.OnCategorySelectedListener {

    private static final int INTERNET_PERMISSION_REQUEST_CODE = 1;
    private NowPlayingFragment nowPlayingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Проверка и запрос разрешения на интернет
        checkInternetPermission();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация NowPlayingFragment
        nowPlayingFragment = NowPlayingFragment.newInstance("Москва", "2025");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nowPlayingFragmentContainer, nowPlayingFragment)
                .commit();

        // Инициализация FilmCategoryFragment
        FilmCategoryFragment filmCategoryFragment = FilmCategoryFragment.newInstance("категории", "2025");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.categoriesFragmentContainer, filmCategoryFragment)
                .commit();

        // Настройка кнопки корзины
        ImageButton busketImageButton = findViewById(R.id.basketButton);
        busketImageButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Корзина открыта", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BasketActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onCategorySelected(String category) {
        // Обновление NowPlayingFragment с новой категорией
        if (nowPlayingFragment != null) {
            nowPlayingFragment.updateCategory(category);
        }
    }

    private void checkInternetPermission() {
        // Проверка статуса подключения к интернету
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            return;
        }

        // Интернет-передача данных считается нормальным разрешением (android.permission.INTERNET)
        // Но для надежности проверяем, добавлено ли разрешение в манифест
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Запрос разрешения, хотя обычно оно не требуется в рантайме
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
}