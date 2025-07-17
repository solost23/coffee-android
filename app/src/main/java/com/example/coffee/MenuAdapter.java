package com.example.coffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffee.databinding.MenuItemNormalBinding;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItemData>
{
    private final Context ctx;
    private final List<MenuItemData> items;
    private int selectedPosition = -1;  // 当前选中位置

    public MenuAdapter(Context ctx, List<MenuItemData> items)
    {
        super(ctx, 0, items);
        this.ctx = ctx;
        this.items = items;
    }

    public void setSelectedPosition(int position)
    {
        selectedPosition = position;
        notifyDataSetChanged();  // 重新刷新列表
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent)
    {
        MenuItemNormalBinding binding;

        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(ctx);
            binding = MenuItemNormalBinding.inflate(inflater, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (MenuItemNormalBinding) view.getTag();
        }

        MenuItemData item = items.get(position);
        binding.menuText.setText(item.title);

        // 设置图标: 根据是否选中切图
        if (position == selectedPosition)
        {
            binding.menuIcon.setImageResource(item.iconResSelected);
        } else {
            binding.menuIcon.setImageResource(item.iconResDefault);
        }

        return view;
    }
}
