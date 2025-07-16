package com.example.coffee;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application
{
    private static Context ctx;

    public static Context getCtx()
    {
        return ctx;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        ctx = getApplicationContext();

    }
}
