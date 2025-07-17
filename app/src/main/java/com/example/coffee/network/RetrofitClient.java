package com.example.coffee.network;

import com.example.coffee.MainApplication;
import com.example.coffee.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient
{
    private static Retrofit retrofit;

    public static Retrofit getInstance()
    {
        if (retrofit == null)
        {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .addInterceptor(new TokenInterceptor(MainApplication.getCtx()))
                    .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
