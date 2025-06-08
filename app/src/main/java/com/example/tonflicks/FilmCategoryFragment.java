package com.example.tonflicks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilmCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmCategoryFragment newInstance(String param1, String param2) {
        FilmCategoryFragment fragment = new FilmCategoryFragment();
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
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.list_films);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        List<Category> items = new ArrayList<>(Arrays.asList(
                new Category("Фантастика"),
                new Category("Аниме"),
                new Category("Комедия"),
                new Category("Ужасы"),
                new Category("Драма"),
                new Category("Мелодрама"),
                new Category("Приключения"),
                new Category("Фэнтези"),
                new Category("Боевик"),
                new Category("Документальный"),
                new Category("Исторический"),
                new Category("Криминал"),
                new Category("Детектив"),
                new Category("Мистика"),
                new Category("Триллер"),
                new Category("Военный"),
                new Category("Семейный"),
                new Category("Музыкальный"),
                new Category("Биография"),
                new Category("Спорт"),
                new Category("Романтика"),
                new Category("Артхаус"),
                new Category("Научный"),
                new Category("Психология")
        ));
        CategoryAdapter categoryAdapter = new CategoryAdapter(items);
        recyclerView.setAdapter(categoryAdapter);
    }


}