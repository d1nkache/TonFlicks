package com.example.tonflicks.fragment;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tonflicks.R;
import com.example.tonflicks.client.FilmApi;
import com.example.tonflicks.client.FilmParameterRequest;
import com.example.tonflicks.client.FilmResponse;
import com.example.tonflicks.client.Screening;
import com.example.tonflicks.recyclerView.Film;
import com.example.tonflicks.recyclerView.FilmAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "NowPlayingFragment";
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1); // e.g., "Москва"
            mParam2 = getArguments().getString(ARG_PARAM2); // e.g., "2025"
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list_films);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        loadFilms(mParam1, "Все", mParam2); // Используем "Драма" как начальную категорию
    }

    public void updateCategory(String newCategory) {
        // Обновление списка фильмов с новой категорией
        loadFilms(mParam1, newCategory, mParam2); // Передаем адрес, новую категорию и год
    }

    private void loadFilms(String address, String category, String dateStr) {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FilmApi filmApi = retrofit.create(FilmApi.class);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        FilmParameterRequest request = new FilmParameterRequest(address,category, date);

        filmApi.getFilmsByParameter(request).enqueue(new Callback<List<FilmResponse>>() {
            @Override
            public void onResponse(Call<List<FilmResponse>> call, Response<List<FilmResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FilmResponse> films = response.body();
                    List<Film> items = new ArrayList<>();
                    for (FilmResponse f : films) {
                        items.add(new Film(f.title, f.genre, f.description, f.imageResId, f.rating, f.year));
                    }

                    Log.d(TAG, "Successfully loaded films. Count: " + films.size() + category);
                    for (FilmResponse film : films) {
                        Log.d(TAG, "Film: title=" + film.title + ", genre=" + film.genre + ", rating=" + film.rating);
                    }

                    FilmAdapter adapter = new FilmAdapter(items);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Failed to load films. Response code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(getContext(), "Ошибка загрузки фильмов: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}