package com.example.tonflicks.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.tonflicks.R;
import com.example.tonflicks.recyclerView.Category;
import com.example.tonflicks.recyclerView.CategoryAdapter;
import com.example.tonflicks.recyclerView.DateSlot;
import com.example.tonflicks.recyclerView.DateSlotAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeatSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeatSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeatSelection.
     */
    // TODO: Rename and change types and number of parameters
    public static SeatSelectionFragment newInstance(String param1, String param2) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
            Button seat = new Button(getContext());
            seat.setText(String.valueOf(i + 1));
            seat.setBackgroundResource(R.drawable.bg_seat_available);
            seat.setTextColor(Color.WHITE);
            seat.setPadding(0, 0, 0, 0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(8, 8, 8, 8);
            seat.setLayoutParams(params);

            seat.setOnClickListener(v -> {
                v.setBackgroundResource(R.drawable.bg_seat_selected);
            });

            gridSeats.addView(seat);
        }
    }
}
