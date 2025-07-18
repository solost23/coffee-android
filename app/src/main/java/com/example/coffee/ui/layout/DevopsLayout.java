package com.example.coffee.ui.layout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.coffee.R;
import com.example.coffee.databinding.ItemFunctionCardBinding;
import com.example.coffee.databinding.LayoutDevopsBinding;
import com.example.coffee.ui.MainActivity;

public class DevopsLayout
{
    private static final String[] labels = {
            "试做模式", "冲洗消毒", "整机清洗", "抬起奶缸", "奶缸复位", "一键恢复"
    };
    private static final int[] cardIcons = {
            R.drawable.ic_test_mode, R.drawable.ic_clean, R.drawable.ic_full_clean,
            R.drawable.ic_milk_up, R.drawable.ic_milk_reset, R.drawable.ic_recover
    };

    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("DevopsLayout", "运维管理");

        LayoutDevopsBinding binding = LayoutDevopsBinding.inflate(activity.getLayoutInflater());

        activity.getBinding().contentFrame.addView(binding.getRoot());

        // 初始化功能卡片
        for (int i = 0; i != labels.length; i ++)
        {
            ItemFunctionCardBinding cardBinding = ItemFunctionCardBinding.inflate(
                    LayoutInflater.from(activity),
                    binding.gridFunctions,
                    false
            );

            cardBinding.cardIcon.setImageResource(cardIcons[i]);
            cardBinding.cardText.setText(labels[i]);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 3, 1f);
            params.rowSpec = GridLayout.spec(i / 3);
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            cardBinding.getRoot().setLayoutParams(params);

            binding.gridFunctions.addView(cardBinding.getRoot());
        }

        return null;
    }
}
