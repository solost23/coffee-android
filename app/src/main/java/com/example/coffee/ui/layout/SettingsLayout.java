package com.example.coffee.ui.layout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffee.databinding.LayoutSettingsBinding;
import com.example.coffee.model.response.DeviceDetailResponse;
import com.example.coffee.model.response.ResultDTO;
import com.example.coffee.ui.LoginActivity;
import com.example.coffee.ui.MainActivity;
import com.example.coffee.ui.viewmodel.DeviceDetailViewModel;
import com.example.coffee.utils.Constants;

public class SettingsLayout
{
    public static Void onCreate(MainActivity activity)
    {
        activity.getBinding().contentFrame.removeAllViews();
        Log.v("SettingLayout", "系统设置");

        // 加载布局
        LayoutSettingsBinding binding = LayoutSettingsBinding.inflate(activity.getLayoutInflater());

        // 添加到 view
        activity.getBinding().contentFrame.addView(binding.getRoot());

        // 显示进度条
        binding.progress.setVisibility(View.VISIBLE);


        // 接口请求数据
        SharedPreferences sp = activity.getSharedPreferences(Constants.PREF_NAME, activity.MODE_PRIVATE);
        String token = sp.getString(Constants.USER_TOKEN, null);
        String serialNumber = sp.getString(Constants.SERIAL_NUMBER, null);
        if (token == null || serialNumber == null)
        {
            Toast.makeText(activity, "参数错误", Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
            return null;
        }

        DeviceDetailViewModel viewModel = new ViewModelProvider(activity).get(DeviceDetailViewModel.class);

        viewModel.getDeviceDetailState().observe(activity, new Observer<DeviceDetailViewModel.DeviceDetailState>() {
            @Override
            public void onChanged(DeviceDetailViewModel.DeviceDetailState state) {
                binding.progress.setVisibility(state == DeviceDetailViewModel.DeviceDetailState.LOADING ? View.VISIBLE : View.GONE);

                switch (state) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        ResultDTO<DeviceDetailResponse> response = viewModel.getResponse();
                        if (response != null && response.getData() != null)
                        {
                            // updateUI(binding, response.getData());

                            // UI 设置
                            DeviceDetailResponse data = response.getData();
                            binding.tvDeviceName.setText("设备名称: " + data.getName());
                            binding.tvSerialNumber.setText("序列号: " + data.getSerialNumber());
                            binding.tvOperationStatus.setText("运营状态: " + parseOperationStatus(data.getOperationStatus()));
                            binding.tvStatus.setText("当前状态: " + parseStatus(data.getStatus()));
                        }
                        break;
                    case API_ERROR:
                        Toast.makeText(activity, "接口返回错误", Toast.LENGTH_LONG).show();
                        break;
                    case NETWORK_ERROR:
                        Toast.makeText(activity, "网络异常，请检查链接", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // 首次加载数据
        viewModel.getDeviceDetail(token, serialNumber);

        return null;
    }

    private static String parseOperationStatus(Integer status)
    {
        if (status == null)
        {
            return "未知";
        }

        switch (status)
        {
            case 0:
                return "关闭";
            case 1:
                return "开启";
        }
        return "未知";
    }

    //    设备状态（如 0-正常、1-离线、2-升级中 3-中断 4-设备清洗 5-设备消毒
//    6-产品制作 7-等待取杯 8-警告 9-一键恢复中 10-奶缸移位中 11-奶缸待复位
//    12-奶缸复位中 13-执行动作）
    private static String parseStatus(Integer status)
    {
        if (status == null)
        {
            return "未知";
        }

        switch(status)
        {
            case 0:
                return "正常";
            case 1:
                return "离线";
            case 2:
                return "升级中";
            case 3:
                return "中断";
            case 4:
                return "设备清洗";
            case 5:
                return "设备消毒";
            case 6:
                return "产品制作";
            case 7:
                return "等待取杯";
            case 8:
                return "警告";
            case 9:
                return "一键恢复中";
            case 10:
                return "奶缸移位中";
            case 11:
                return "奶缸待复位";
            case 12:
                return "奶缸复位中";
            case 13:
                return "执行动作";
        }
        return "未知";
    }
}
