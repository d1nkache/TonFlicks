package com.example.tonflicks.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tonflicks.R;
import com.example.tonflicks.client.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SeatSelectionListener listener;
    private final List<Seat> selectedSeats = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    public SeatSelectionFragment() {}

    public interface SeatSelectionListener {
        void onSeatsSelected(List<Seat> selectedSeats);
    }

    public static SeatSelectionFragment newInstance(String param1, String param2) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSeatSelectionListener(SeatSelectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GridLayout gridSeats = view.findViewById(R.id.gridSeats);

        int rows = 5;
        int cols = 8;
        gridSeats.setRowCount(rows);
        gridSeats.setColumnCount(cols);

        for (int i = 0; i < rows * cols; i++) {
            final int seatNumber = i + 1;
            Button seat = new Button(getContext());
            seat.setText(String.valueOf(seatNumber));
            seat.setBackgroundResource(R.drawable.bg_seat_available);
            seat.setTextColor(Color.WHITE);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(8, 8, 8, 8);
            seat.setLayoutParams(params);

            seat.setOnClickListener(v -> {
                boolean isSelected = false;
                Seat found = null;
                for (Seat s : selectedSeats) {
                    if (s.getSeatNumber().equals("A" + seatNumber)) {
                        found = s;
                        isSelected = true;
                        break;
                    }
                }

                if (isSelected && found != null) {
                    selectedSeats.remove(found);
                    seat.setBackgroundResource(R.drawable.bg_seat_available);
                } else {
                    selectedSeats.add(new Seat("A" + seatNumber, 500)); // Пример: "A1", цена 500
                    seat.setBackgroundResource(R.drawable.bg_seat_selected);
                }

                if (listener != null) {
                    listener.onSeatsSelected(new ArrayList<>(selectedSeats));
                }
            });

            gridSeats.addView(seat);
        }
    }
}
