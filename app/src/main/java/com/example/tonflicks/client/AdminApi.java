package com.example.tonflicks.client;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminApi {

    @POST("/film/addFilm")
    Call<Void> addFilm(@Body FilmRequest request);

    @PUT("/user/role/{id}")
    Call<Void> grantAdmin(@Path("id") long userId);
}
