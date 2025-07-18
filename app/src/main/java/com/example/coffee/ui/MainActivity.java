package com.example.coffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.widget.Toast;

import com.example.coffee.MenuAdapter;
import com.example.coffee.MenuItemData;
import com.example.coffee.R;
import com.example.coffee.databinding.ActivityMainBinding;
import com.example.coffee.databinding.MenuItemHeaderBinding;
import com.example.coffee.ui.layout.DevopsLayout;
import com.example.coffee.ui.layout.MaterialLayout;
import com.example.coffee.ui.layout.OrderLayout;
import com.example.coffee.ui.layout.SettingsLayout;
import com.example.coffee.ui.layout.ThemeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;

    public ActivityMainBinding getBinding()
    {
        return this.binding;
    }

    private List<MenuItemData> menuItems  = Arrays.asList(
            new MenuItemData("运维管理", R.drawable.ic_ops1, R.drawable.ic_ops),
            new MenuItemData("物料管理", R.drawable.ic_meterial1, R.drawable.ic_meterial),
            new MenuItemData("制作订单", R.drawable.ic_order1, R.drawable.ic_order),
            new MenuItemData("系统设置", R.drawable.ic_settings1, R.drawable.ic_settings),
            new MenuItemData("主题设置", R.drawable.ic_theme1, R.drawable.ic_theme)
    );

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

        // 加载并绑定 HeaderView
        MenuItemHeaderBinding headerBinding = MenuItemHeaderBinding.inflate(getLayoutInflater());
        binding.leftMenu.addHeaderView(headerBinding.getRoot());

        // 加载主内容
        DevopsLayout.onCreate(this);

        MenuAdapter adapter = new MenuAdapter(this, menuItems);
        binding.leftMenu.setAdapter(adapter);

        // 菜单点击事件
        binding.leftMenu.setOnItemClickListener((parent, view, position, id) -> {
            position = position - binding.leftMenu.getHeaderViewsCount();
            if (position >= 0 && position < menuItems.size()) {
                adapter.setSelectedPosition(position); // 图标变更
                binding.leftMenu.setBackgroundResource(backgroundResArray[position]);  // 切换背景图

                Function<MainActivity, Void> handle = getFunctionMap().get(menuItems.get(position).title);
                if (handle == null) {
                    Log.v("MainActivity", "Event " + menuItems.get(position).title + " is currently not supported.");
                    return;
                }

                handle.apply(this);
            }
        });

        // header点击事件
        headerBinding.getRoot().setOnClickListener(v -> {
            Toast.makeText(this, "点击了 Header", Toast.LENGTH_SHORT).show();
        });
    }

    private static Map<String, Function<MainActivity, Void>> getFunctionMap()
    {
        Map<String, Function<MainActivity, Void>> fm = new HashMap<>();

        fm.put("运维管理", DevopsLayout::onCreate);
        fm.put("物料管理", MaterialLayout::onCreate);
        fm.put("制作订单", OrderLayout::onCreate);
        fm.put("系统设置", SettingsLayout::onCreate);
        fm.put("主题设置", ThemeLayout::onCreate);

        return fm;
    }
}
