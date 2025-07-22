package com.example.coffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.coffee.R;
import com.example.coffee.databinding.ActivityMainBinding;
import com.example.coffee.ui.layout.DevopsLayout;
import com.example.coffee.ui.layout.MaterialLayout;
import com.example.coffee.ui.layout.OrderLayout;
import com.example.coffee.ui.layout.SettingsLayout;
import com.example.coffee.ui.layout.ThemeLayout;
import com.example.coffee.utils.Constants;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;

    public ActivityMainBinding getBinding()
    {
        return this.binding;
    }

//    private List<MenuItemData> menuItems  = Arrays.asList(
//            new MenuItemData("运维管理", R.drawable.ic_ops1, R.drawable.ic_ops),
//            new MenuItemData("物料管理", R.drawable.ic_meterial1, R.drawable.ic_meterial),
//            new MenuItemData("制作订单", R.drawable.ic_order1, R.drawable.ic_order),
//            new MenuItemData("系统设置", R.drawable.ic_settings1, R.drawable.ic_settings),
//            new MenuItemData("主题设置", R.drawable.ic_theme1, R.drawable.ic_theme)
//    );

    private int[] backgroundResArray = {
            R.drawable.ic_menu_bg,
            R.drawable.ic_menu_bg1,
            R.drawable.ic_menu_bg2,
            R.drawable.ic_menu_bg3,
            R.drawable.ic_menu_bg4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 读取主题
        {
            SharedPreferences sp = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
            String background = sp.getString(Constants.BACKGROUND, "0");
            String menuBackground = sp.getString(Constants.MENU_BACKGROUND, "0");
            switch (background)
            {
                case "0":
                    binding.getRoot().setBackgroundResource(R.drawable.ic_theme_bg1);
                    break;
                case "1":
                    binding.getRoot().setBackgroundResource(R.drawable.ic_theme_bg2);
                    break;
                case "2":
                    binding.getRoot().setBackgroundResource(R.drawable.ic_theme_bg3);
                    break;
                case "3":
                    binding.getRoot().setBackgroundResource(R.drawable.ic_theme_bg4);
                    break;
            }

            switch (menuBackground)
            {
                case "0":
                    break;
                case "1":
                    break;
            }
        }


        // 默认加载运维管理页面
        DevopsLayout.onCreate(this);
        updateMenuSelection(0);

        // 菜单点击逻辑
        binding.menuItemDevops.setOnClickListener(v -> {
            DevopsLayout.onCreate(this);
            updateMenuSelection(0);
        });

        binding.menuItemMaterial.setOnClickListener(v -> {
            MaterialLayout.onCreate(this);
            updateMenuSelection(1);
        });

        binding.menuItemOrder.setOnClickListener(v -> {
            OrderLayout.onCreate(this);
            updateMenuSelection(2);
        });

        binding.menuItemSettings.setOnClickListener(v -> {
            SettingsLayout.onCreate(this);
            updateMenuSelection(3);
        });

        binding.menuItemTheme.setOnClickListener(v -> {
            ThemeLayout.onCreate(this);
            updateMenuSelection(4);
        });
    }

    private void updateMenuSelection(int pos)
    {
        // 设置菜单背景图
        binding.menuBarBg.setBackgroundResource(backgroundResArray[pos]);

        // 所有菜单项图标复原
        binding.menuItemDevopsIcon.setImageResource(R.drawable.ic_ops1);
        binding.menuItemMaterialIcon.setImageResource(R.drawable.ic_meterial1);
        binding.menuItemOrderIcon.setImageResource(R.drawable.ic_order1);
        binding.menuItemSettingsIcon.setImageResource(R.drawable.ic_settings1);
        binding.menuItemThemeIcon.setImageResource(R.drawable.ic_theme1);

        // 字体颜色复原
        binding.menuItemDevopsText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        binding.menuItemMaterialText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        binding.menuItemOrderText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        binding.menuItemSettingsText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));
        binding.menuItemThemeText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black));

        // 设置当前选中图标及字体颜色
        switch (pos)
        {
            case 0:
                binding.menuItemDevopsIcon.setImageResource(R.drawable.ic_ops);
                binding.menuItemDevopsText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.coffee_brown));
                break;
            case 1:
                binding.menuItemMaterialIcon.setImageResource(R.drawable.ic_meterial);
                binding.menuItemMaterialText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.coffee_brown));
                break;
            case 2:
                binding.menuItemOrderIcon.setImageResource(R.drawable.ic_order);
                binding.menuItemOrderText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.coffee_brown));
                break;
            case 3:
                binding.menuItemSettingsIcon.setImageResource(R.drawable.ic_settings);
                binding.menuItemSettingsText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.coffee_brown));
                break;
            case 4:
                binding.menuItemThemeIcon.setImageResource(R.drawable.ic_theme);
                binding.menuItemThemeText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.coffee_brown));
                break;
        }

    }

//    private static Map<String, Function<MainActivity, Void>> getFunctionMap()
//    {
//        Map<String, Function<MainActivity, Void>> fm = new HashMap<>();
//
//        fm.put("运维管理", DevopsLayout::onCreate);
//        fm.put("物料管理", MaterialLayout::onCreate);
//        fm.put("制作订单", OrderLayout::onCreate);
//        fm.put("系统设置", SettingsLayout::onCreate);
//        fm.put("主题设置", ThemeLayout::onCreate);
//
//        return fm;
//    }
}
