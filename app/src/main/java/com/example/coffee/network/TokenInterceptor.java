package com.example.coffee.network;

import com.example.coffee.ui.LoginActivity;
import com.example.coffee.model.response.ResultDTO;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.MediaType;


public class TokenInterceptor implements Interceptor
{
    private final Context ctx;

    public TokenInterceptor(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();

        if (body == null)
        {
            return response;
        }

        MediaType contentType = body.contentType();
        String content = body.string();
        try {
            ResultDTO<?> result = new Gson().fromJson(content, ResultDTO.class);
            String code = result.getCode();

            if (code.equals("1998") || code.equals("1999"))
            {
                clear();

                new Handler(Looper.getMainLooper()).post(() -> {
                    Intent intent = new Intent(this.ctx, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    this.ctx.startActivity(intent);
                    Toast.makeText(this.ctx, "登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
                });
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return response.newBuilder()
                .body(ResponseBody.create(contentType, content))
                .build();
    }

    private void clear()
    {
        SharedPreferences sp = this.ctx.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        sp.edit().remove("Authorization").apply();
    }
}
