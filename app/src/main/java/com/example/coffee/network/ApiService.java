package com.example.coffee.network;

import com.example.coffee.model.request.LoginRequest;
import com.example.coffee.model.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService
{
    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate, br",
            "Connection: keep-alive",
            "Content-Type: application/json"
    })
    @POST("user/login/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
