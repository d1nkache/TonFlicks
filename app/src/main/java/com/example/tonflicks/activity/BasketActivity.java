package com.example.tonflicks.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tonflicks.R;
import com.example.tonflicks.recyclerView.Basket;
import com.example.tonflicks.recyclerView.BasketAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Date;

public class BasketActivity extends AppCompatActivity {

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

        RecyclerView rv = findViewById(R.id.rvBasket);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Basket basket = new Basket();
        basket.addTicket(new Basket.Ticket(
                "1", "Inception", "https://example.com/poster.jpg",
                "Cinema City", "123 Main St", "18:00", "Hall 1",
                "TICKET123", 10.99, new Date()
        ));
        basket.addTicket(new Basket.Ticket(
                "1", "Inception", "https://example.com/poster.jpg",
                "Cinema City", "123 Main St", "18:00", "Hall 1",
                "TICKET123", 10.99, new Date()
        ));
        rv.setAdapter(new BasketAdapter(basket.getTickets()));

    }
}