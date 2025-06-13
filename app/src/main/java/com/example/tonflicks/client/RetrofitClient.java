package com.example.tonflicks.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    FilmApi filmApi = retrofit.create(FilmApi.class);

}
