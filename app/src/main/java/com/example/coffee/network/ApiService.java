package com.example.coffee.network;

import com.example.coffee.model.request.DeviceOperationStatusUpdateRequest;
import com.example.coffee.model.request.LoginRequest;
import com.example.coffee.model.response.DeviceDetailResponse;
import com.example.coffee.model.response.ResultDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService
{
    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate, br",
            "Connection: keep-alive",
            "Content-Type: application/json"
    })
    @POST("/gw/v1/user/login/login")
    Call<ResultDTO<String>> login(@Body LoginRequest request);

    @GET("/gw/v1/user/device/detail")
    Call<ResultDTO<DeviceDetailResponse>> getDeviceDetail(
        @Header("Authorization") String token,
        @Query("serialNumber") String serialNumber
    );

    @PUT("/gw/v1/user/devops/deviceOperationStatusUpdate")
    Call<ResultDTO<String>> deviceOperationStatusUpdate(
            @Header("Authorization") String token,
            @Body DeviceOperationStatusUpdateRequest request
    );
}
