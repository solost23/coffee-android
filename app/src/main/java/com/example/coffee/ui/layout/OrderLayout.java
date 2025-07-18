package com.example.coffee.ui.layout;

import android.util.Log;

import com.example.coffee.databinding.LayoutOrderBinding;
import com.example.coffee.ui.MainActivity;

public class OrderLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("OrderLayout", "制作订单");

        LayoutOrderBinding binding = LayoutOrderBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        return null;
    }
}
