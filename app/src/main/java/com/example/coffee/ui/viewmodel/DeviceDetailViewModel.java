package com.example.coffee.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffee.model.response.DeviceDetailResponse;
import com.example.coffee.network.ApiService;
import com.example.coffee.network.RetrofitClient;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailViewModel extends ViewModel
{
    private DeviceDetailResponse response;

    private final ApiService apiService;

    private final MutableLiveData<DeviceDetailState> deviceDetailState = new MutableLiveData<>();

    public enum DeviceDetailState {
        LOADING,
        SUCCESS,
        API_ERROR,
        NETWORK_ERROR
    }

    public DeviceDetailResponse getResponse()
    {
        return this.response;
    }

    public DeviceDetailViewModel() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<DeviceDetailState> getDeviceDetailState() {
        return deviceDetailState;
    }

    public void getDeviceDetail(String token, String serialNumber)
    {
        deviceDetailState.postValue(DeviceDetailState.LOADING);

        apiService.getDeviceDetail(token, serialNumber).enqueue(new Callback<DeviceDetailResponse>() {
            @Override
            public void onResponse(Call<DeviceDetailResponse> call, Response<DeviceDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DeviceDetailViewModel.this.response = response.body();
                    if ("SUCCESS".equals(DeviceDetailViewModel.this.response.getCode())) {
                        deviceDetailState.postValue(DeviceDetailState.SUCCESS);
                    } else {
                        deviceDetailState.postValue(DeviceDetailState.API_ERROR);
                    }
                } else {
                    deviceDetailState.postValue(DeviceDetailState.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<DeviceDetailResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    deviceDetailState.postValue(DeviceDetailViewModel.DeviceDetailState.NETWORK_ERROR);
                } else {
                    deviceDetailState.postValue(DeviceDetailViewModel.DeviceDetailState.NETWORK_ERROR);
                }
            }
        });
    }
}
