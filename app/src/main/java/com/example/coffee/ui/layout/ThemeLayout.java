package com.example.coffee.ui.layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.coffee.R;
import com.example.coffee.databinding.LayoutThemeBinding;
import com.example.coffee.ui.MainActivity;
import com.example.coffee.utils.Constants;

public class ThemeLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("ThemeLayout", "主题设置");

        LayoutThemeBinding binding = LayoutThemeBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        init(activity, binding);

        SharedPreferences sp = activity.getSharedPreferences(Constants.PREF_NAME, activity.MODE_PRIVATE);
        // 单击事件
        // 背景图
        binding.background1.setOnClickListener(v -> {
            initBackground(binding);
            apply(
                    binding.background1,
                    R.drawable.border_template,
                    R.drawable.ic_theme_bg1,
                    R.drawable.ic_theme_bg_a
            );
            activity.getBinding().getRoot().setBackgroundResource(R.drawable.ic_theme_bg1);
            sp.edit()
                    .putString(Constants.BACKGROUND, "0")
                    .apply();
        });
        binding.background2.setOnClickListener(v -> {
            // 清除所有背景图边框
            initBackground(binding);
            // 设置当前背景图边框
            apply(
                    binding.background2,
                    R.drawable.border_template,
                    R.drawable.ic_theme_bg2,
                    R.drawable.ic_theme_bg_a
            );

            // 设置整体背景
            activity.getBinding().getRoot().setBackgroundResource(R.drawable.ic_theme_bg2);
            sp.edit()
                    .putString(Constants.BACKGROUND, "1")
                    .apply();
        });
        binding.background3.setOnClickListener(v -> {
            initBackground(binding);
            apply(
                    binding.background3,
                    R.drawable.border_template,
                    R.drawable.ic_theme_bg3,
                    R.drawable.ic_theme_bg_a
                    );
            activity.getBinding().getRoot().setBackgroundResource(R.drawable.ic_theme_bg3);
            sp.edit()
                    .putString(Constants.BACKGROUND, "2")
                    .apply();
        });
        binding.background4.setOnClickListener(v -> {
            initBackground(binding);
            apply(
                    binding.background4,
                    R.drawable.border_template,
                    R.drawable.ic_theme_bg4,
                    R.drawable.ic_theme_bg_a
            );
            activity.getBinding().getRoot().setBackgroundResource(R.drawable.ic_theme_bg4);
            sp.edit()
                    .putString(Constants.BACKGROUND, "3")
                    .apply();
        });

        // 菜单风格
        binding.menuBg1.setOnClickListener( v -> {
            initMenuBackground(binding);
            apply(
                binding.menuBg1,
                R.drawable.border_template,
                R.drawable.ic_theme_menu_bg1,
                R.drawable.ic_theme_bg_m
            );
            sp.edit()
                    .putString(Constants.MENU_BACKGROUND, "0")
                    .apply();
        });
        binding.menuBg2.setOnClickListener(v -> {
            initMenuBackground(binding);
            apply(
                    binding.menuBg2,
                    R.drawable.border_template,
                    R.drawable.ic_theme_menu_bg2,
                    R.drawable.ic_theme_bg_m
            );
            sp.edit()
                    .putString(Constants.MENU_BACKGROUND, "1")
                    .apply();
        });

        return null;
    }

    private static void init(MainActivity activity, LayoutThemeBinding binding)
    {
        initBackground(binding);
        // 从本地获取数据，若本地没有，使用默认的
        SharedPreferences sp = activity.getSharedPreferences(Constants.PREF_NAME, activity.MODE_PRIVATE);

        String background = sp.getString(Constants.BACKGROUND, "0");
        String menuBackground = sp.getString(Constants.MENU_BACKGROUND, "0");

        switch (background)
        {
            case "0":
                apply(
                        binding.background1,
                        R.drawable.border_template,
                        R.drawable.ic_theme_bg1,
                        R.drawable.ic_theme_bg_a
                );
                break;
            case "1":
                apply(
                        binding.background2,
                        R.drawable.border_template,
                        R.drawable.ic_theme_bg2,
                        R.drawable.ic_theme_bg_a
                );
                break;
            case "2":
                apply(
                        binding.background3,
                        R.drawable.border_template,
                        R.drawable.ic_theme_bg3,
                        R.drawable.ic_theme_bg_a
                );
                break;
            case "3":
                apply(
                        binding.background4,
                        R.drawable.border_template,
                        R.drawable.ic_theme_bg4,
                        R.drawable.ic_theme_bg_a
                );
                break;
        }

        initMenuBackground(binding);
        switch (menuBackground)
        {
            case "0":
                apply(
                        binding.menuBg1,
                        R.drawable.border_template,
                        R.drawable.ic_theme_menu_bg1,
                        R.drawable.ic_theme_bg_m
                );
                break;
            case "1":
                apply(
                        binding.menuBg2,
                        R.drawable.border_template,
                        R.drawable.ic_theme_menu_bg2,
                        R.drawable.ic_theme_bg_m
                );
                break;
        }
    }

    // 设置背景图
    private static void initBackground (LayoutThemeBinding binding)
    {
        apply(
                binding.background1,
                R.drawable.border_template,
                R.drawable.ic_theme_bg1,
                -1
        );
        apply(
                binding.background2,
                R.drawable.border_template,
                R.drawable.ic_theme_bg2,
                -1 // 代表不需要设置边框
        );
        apply(
                binding.background3,
                R.drawable.border_template,
                R.drawable.ic_theme_bg3,
                -1
        );
        apply(
                binding.background4,
                R.drawable.border_template,
                R.drawable.ic_theme_bg4,
                -1
        );
    }

    // 设置菜单风格
    private static void initMenuBackground(LayoutThemeBinding binding)
    {
        apply(
                binding.menuBg1,
                R.drawable.border_template,
                R.drawable.ic_theme_menu_bg1,
                -1
        );
        apply(
                binding.menuBg2,
                R.drawable.border_template,
                R.drawable.ic_theme_menu_bg2,
                -1
        );
    }

    private static void apply(
            @NonNull View view,
            @DrawableRes int res,
            @DrawableRes int back,
            @DrawableRes int bord
    ) {
        Context ctx = view.getContext();

        Drawable backDrawable = ContextCompat.getDrawable(ctx, back);
        Drawable bordDrawable = (bord != -1) ? ContextCompat.getDrawable(ctx, bord) : null;

        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(ctx, res);
        if (layerDrawable == null || backDrawable == null)
        {
            Log.e("LayerDrawableHelper", "One of the drawables is null");
            return;
        }
        if (bord != -1 && bordDrawable == null)
        {
            Log.e("LayerDrawableHelper", "One of the drawables is null");
            return;
        }

        // 替换图层
        layerDrawable.setDrawableByLayerId(R.id.layer_background, backDrawable);
        if (bord != -1)
        {
            layerDrawable.setDrawableByLayerId(R.id.layer_border, bordDrawable);
        }
        view.setBackground(layerDrawable);
    }
}
