package com.example.coffee.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffee.model.request.LoginRequest;
import com.example.coffee.model.response.ResultDTO;
import com.example.coffee.network.ApiService;
import com.example.coffee.network.RetrofitClient;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel
{
    private ResultDTO<String> response;

    private final ApiService apiService;

    // 使用LiveData暴露三种状态
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();

    public enum LoginState {
        LOADING,
        SUCCESS,
        API_ERROR,
        NETWORK_ERROR
    }

    public ResultDTO<String> getResponse()
    {
        return this.response;
    }

    public LoginViewModel() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    // 暴露不可变的LiveData给Activity
    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public void login(String account, String password) {
        // 开始加载
        loginState.postValue(LoginState.LOADING);

        LoginRequest.Data data = new LoginRequest.Data(
                account,
                password,
                "android",
                "M120210006"
        );

        apiService.login(new LoginRequest(data)).enqueue(new Callback<ResultDTO<String>>() {
            @Override
            public void onResponse(Call<ResultDTO<String>> call, Response<ResultDTO<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginViewModel.this.response = response.body();
                    if ("SUCCESS".equals(LoginViewModel.this.response.getCode())) {
                        loginState.postValue(LoginState.SUCCESS);
                    } else {
                        // 业务错误（如密码错误）
                        loginState.postValue(LoginState.API_ERROR);
                    }
                } else {
                    // HTTP错误（如404/500）
                    loginState.postValue(LoginState.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResultDTO<String>> call, Throwable t) {
                // 网络错误（如超时）
                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    loginState.postValue(LoginState.NETWORK_ERROR);
                } else {
                    loginState.postValue(LoginState.NETWORK_ERROR);
                }
            }
        });
    }
}