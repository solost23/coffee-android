package com.example.coffee.network;

import com.example.coffee.model.request.LoginRequest;
import com.example.coffee.model.response.LoginResponse;
import com.example.coffee.model.response.DeviceDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

    @GET("user/device/detail")
    Call<DeviceDetailResponse> getDeviceDetail(
        @Header("Authorization") String token,
        @Query("serialNumber") String serialNumber
    );
}
