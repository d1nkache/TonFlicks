package com.example.tonflicks.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FilmApi {
    @POST("/film/getFilmByParams")
    Call<List<FilmResponse>> getFilmsByParameter(@Body FilmParameterRequest request);
}