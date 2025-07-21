package com.example.coffee.ui.layout;

import android.util.Log;
import com.example.coffee.databinding.LayoutDevopsBinding;
import com.example.coffee.ui.MainActivity;

public class DevopsLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("DevopsLayout", "运维管理");

        LayoutDevopsBinding binding = LayoutDevopsBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        return null;
    }
}
