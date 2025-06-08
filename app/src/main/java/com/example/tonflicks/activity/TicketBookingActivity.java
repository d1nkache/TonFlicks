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
import com.example.tonflicks.fragment.SeatSelectionFragment;
import com.example.tonflicks.recyclerView.DateSlot;
import com.example.tonflicks.recyclerView.DateSlotAdapter;
import com.example.tonflicks.recyclerView.TimeSlot;
import com.example.tonflicks.recyclerView.TimeSlotAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketBookingActivity extends AppCompatActivity {

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

        RecyclerView rvTime = findViewById(R.id.recyclerViewTime);
        rvTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<TimeSlot> times = Arrays.asList(
                new TimeSlot("12:00"),
                new TimeSlot("14:30"),
                new TimeSlot("16:00"),
                new TimeSlot("18:20"),
                new TimeSlot("20:00")
        );
        TimeSlotAdapter adapter = new TimeSlotAdapter(times);
        rvTime.setAdapter(adapter);

        RecyclerView recyclerView =findViewById(R.id.recyclerViewDate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        ArrayList<DateSlot> items = new ArrayList<>(Arrays.asList(
                new DateSlot("12 июня"),
                new DateSlot("13 июня"),
                new DateSlot("14 июня"),
                new DateSlot("15 июня"),
                new DateSlot("16 июня"),
                new DateSlot("17 июня"),
                new DateSlot("18 июня"),
                new DateSlot("19 июня"),
                new DateSlot("20 июня"),
                new DateSlot("21 июня"),
                new DateSlot("22 июня"),
                new DateSlot("23 июня"),
                new DateSlot("24 июня"),
                new DateSlot("25 июня"),
                new DateSlot("26 июня")
        ));
        DateSlotAdapter dateSlotAdapter = new DateSlotAdapter(items);
        recyclerView.setAdapter(dateSlotAdapter);

        SeatSelectionFragment seatSelectionFragment = SeatSelectionFragment.newInstance("категории", "2025");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.seatSelectionContainer, seatSelectionFragment)
                .commit();


    }
}