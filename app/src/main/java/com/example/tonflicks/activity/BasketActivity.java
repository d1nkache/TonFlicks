package com.example.tonflicks.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tonflicks.R;
import com.example.tonflicks.client.CartApi;
import com.example.tonflicks.client.CartResponse;
import com.example.tonflicks.client.TicketResponse;
import com.example.tonflicks.recyclerView.Basket;
import com.example.tonflicks.recyclerView.BasketAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasketActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.basketActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbarBasket);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rv = findViewById(R.id.rvBasket);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCart(userId);
    }

    private void loadCart(int userId) {
        Log.d("BasketActivity", "Загрузка корзины для userId: " + userId);
        Toast.makeText(this, "Загрузка корзины...", Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartApi cartApi = retrofit.create(CartApi.class);
        Call<List<CartResponse>> call = cartApi.getCartByUser(userId);

        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    CartResponse cart = response.body().get(0); // предполагаем одну корзину

                    Basket basket = new Basket();
                    for (TicketResponse t : cart.getTickets()) {
                        basket.addTicket(new Basket.Ticket(
                                String.valueOf(t.getTicketId()),
                                t.getFilmTitle(),
                                t.getPosterUrl(),
                                t.getCinemaName(),
                                t.getCinemaAddress(),
                                t.getTime(),
                                t.getHallName(),
                                t.getTicketCode(),
                                t.getPrice(),
                                new Date()
                        ));
                    }

                    rv.setAdapter(new BasketAdapter(basket.getTickets()));
                    Toast.makeText(BasketActivity.this, "Корзина загружена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BasketActivity.this, "Корзина пуста или ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                Log.e("BasketActivity", "Ошибка загрузки: ", t);
                Toast.makeText(BasketActivity.this, "Ошибка загрузки: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
