package com.example.tonflicks.client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/user/login")
    Call<LoginResponse> login(@Body UserCredRequest request);

    @POST("/user/register")
    Call<UserDto> registerUser(@Body SignUpRequest request);
}
