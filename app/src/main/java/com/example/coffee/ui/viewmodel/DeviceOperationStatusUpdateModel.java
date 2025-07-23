package com.example.coffee.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffee.model.request.DeviceOperationStatusUpdateRequest;
import com.example.coffee.model.response.ResultDTO;
import com.example.coffee.network.ApiService;
import com.example.coffee.network.RetrofitClient;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceOperationStatusUpdateModel extends ViewModel
{
    private ResultDTO<String> response;

    private final ApiService apiService;

    private final MutableLiveData<DeviceOperationStatusUpdateModel.DeviceOperationStatusUpdateState> deviceOperationStatusUpdateState = new MutableLiveData<>();

    public enum DeviceOperationStatusUpdateState {
        LOADING,
        SUCCESS,
        API_ERROR,
        NETWORK_ERROR
    }

    public ResultDTO<String> getResponse()
    {
        return this.response;
    }

    public DeviceOperationStatusUpdateModel()
    {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<DeviceOperationStatusUpdateState> getDeviceOperationStatusUpdateState()
    {
        return this.deviceOperationStatusUpdateState;
    }

    public void deviceOperationStatusUpdate(String token, DeviceOperationStatusUpdateRequest request)
    {
        deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.LOADING);

        apiService.deviceOperationStatusUpdate(token, request).enqueue(new Callback<ResultDTO<String>>() {
            @Override
            public void onResponse(Call<ResultDTO<String>> call, Response<ResultDTO<String>> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    DeviceOperationStatusUpdateModel.this.response = response.body();
                    if ("SUCCESS".equals(DeviceOperationStatusUpdateModel.this.response.getCode()))
                    {
                        deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.SUCCESS);
                    } else {
                        deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.API_ERROR);
                    }
                } else {
                    deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResultDTO<String>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof ConnectException)
                {
                    deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.NETWORK_ERROR);
                } else {
                    deviceOperationStatusUpdateState.postValue(DeviceOperationStatusUpdateState.NETWORK_ERROR);
                }
            }
        });
    }
}
