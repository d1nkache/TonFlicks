package com.example.tonflicks.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApi {
    @GET("/cart/{userId}")
    Call<List<CartResponse>> getCartByUser(@Path("userId") int userId);

    @POST("/cart")
    Call<Void> addToCart(@Body CartRequest request);
}
