package com.example.tonflicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tonflicks.fragment.FilmCategoryFragment;
import com.example.tonflicks.fragment.NowPlayingFragment;
import com.example.tonflicks.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NowPlayingFragment nowPlayingFragment = NowPlayingFragment.newInstance("фильмы", "2025");
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
            Toast.makeText(MainActivity.this, "Корзина открыта", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BasketActivity.class);
            startActivity(intent);
        });
    }
}