package com.example.tonflicks.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FilmApi {
    @POST("/film/addFilm")
    Call<FilmResponse> addFilm(@Body FilmRequest filmRequest);
    @GET("/film/getFilmByTitle/{title}")
    Call<List<FilmResponse>> getFilmsByTitle(@Path("title") String title);

    @POST("/film/getFilmByParams")
    Call<List<FilmResponse>> getFilmsByParameter(@Body FilmParameterRequest request);
}