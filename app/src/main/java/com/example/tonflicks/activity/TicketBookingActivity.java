package com.example.tonflicks.activity;

import static java.security.AccessController.getContext;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.tonflicks.client.CartRequest;
import com.example.tonflicks.client.Seat;
import com.example.tonflicks.client.TicketRequest;
import com.example.tonflicks.fragment.SeatSelectionFragment;
import com.example.tonflicks.recyclerView.DateSlot;
import com.example.tonflicks.recyclerView.DateSlotAdapter;
import com.example.tonflicks.recyclerView.TimeSlot;
import com.example.tonflicks.recyclerView.TimeSlotAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketBookingActivity extends AppCompatActivity implements SeatSelectionFragment.SeatSelectionListener {

    private Button btnPay;
    private TextView selectionText;
    private List<Seat> selectedSeats = new ArrayList<>();
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_booking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.userId = (int) getIntent().getIntExtra("userId", -1);
        String userIdStr = String.valueOf(userId);
        Toast.makeText(this, "userId " + userIdStr, Toast.LENGTH_LONG).show();
        btnPay = findViewById(R.id.btnPay);
        selectionText = findViewById(R.id.selectionText);
        btnPay.setEnabled(false);

        setupDateAndTimeSelectors();

        SeatSelectionFragment fragment = SeatSelectionFragment.newInstance("категории", "2025");
        fragment.setSeatSelectionListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.seatSelectionContainer, fragment)
                .commit();

        btnPay.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) return;

            List<TicketRequest> tickets = new ArrayList<>();
            for (Seat seat : selectedSeats) {
                tickets.add(new TicketRequest(
                        101,
                        seat.getSeatNumber(),
                        seat.getPrice()
                ));
            }

            CartRequest request = new CartRequest(userId, tickets);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CartApi cartApi = retrofit.create(CartApi.class);
            cartApi.addToCart(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(TicketBookingActivity.this, "Билеты куплены!", Toast.LENGTH_SHORT).show();
                    selectedSeats.clear();
                    updateSelectionText();
                    btnPay.setEnabled(false);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(TicketBookingActivity.this, "Ошибка покупки: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void setupDateAndTimeSelectors() {
        RecyclerView rvTime = findViewById(R.id.recyclerViewTime);
        rvTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<TimeSlot> times = Arrays.asList(
                new TimeSlot("12:00"), new TimeSlot("14:30"),
                new TimeSlot("16:00"), new TimeSlot("18:20"),
                new TimeSlot("20:00")
        );
        rvTime.setAdapter(new TimeSlotAdapter(times));

        RecyclerView rvDate = findViewById(R.id.recyclerViewDate);
        rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<DateSlot> dates = Arrays.asList(
                new DateSlot("12 июня"), new DateSlot("13 июня"), new DateSlot("14 июня"),
                new DateSlot("15 июня"), new DateSlot("16 июня"), new DateSlot("17 июня"),
                new DateSlot("18 июня"), new DateSlot("19 июня"), new DateSlot("20 июня"),
                new DateSlot("21 июня"), new DateSlot("22 июня"), new DateSlot("23 июня"),
                new DateSlot("24 июня"), new DateSlot("25 июня"), new DateSlot("26 июня")
        );
        rvDate.setAdapter(new DateSlotAdapter(new ArrayList<>(dates)));
    }

    @Override
    public void onSeatsSelected(List<Seat> selected) {
        this.selectedSeats = selected;
        updateSelectionText();
        btnPay.setEnabled(!selected.isEmpty());
    }

    private void updateSelectionText() {
        selectionText.setText("Выбрано " + selectedSeats.size() + " мест");
    }
}
