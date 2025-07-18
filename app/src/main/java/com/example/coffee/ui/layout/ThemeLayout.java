package com.example.coffee.ui.layout;

import android.util.Log;

import com.example.coffee.databinding.LayoutThemeBinding;
import com.example.coffee.ui.MainActivity;

public class ThemeLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("ThemeLayout", "主题设置");

        LayoutThemeBinding binding = LayoutThemeBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        return null;
    }
}
