package com.example.tonflicks;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TicketBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewTime), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvTime = findViewById(R.id.recyclerViewTime); // если в Fragment
        rvTime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<TimeSlot> times = Arrays.asList(
                new TimeSlot("12:00"),
                new TimeSlot("14:30"),
                new TimeSlot("16:00"),
                new TimeSlot("18:20"),
                new TimeSlot("20:00")
        );
        TimeSlotAdapter adapter = new TimeSlotAdapter(times);

        rvTime.setAdapter(adapter);


    }
}