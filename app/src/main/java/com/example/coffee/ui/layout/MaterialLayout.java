package com.example.coffee.ui.layout;

import android.util.Log;

import com.example.coffee.databinding.LayoutMaterialBinding;
import com.example.coffee.ui.MainActivity;

public class MaterialLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("MaterialLayout", "物料管理");

        LayoutMaterialBinding binding = LayoutMaterialBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        return null;
    }
}
