package com.example.coffee.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffee.databinding.ActivityDeviceDetailBinding;
import com.example.coffee.model.response.DeviceDetailResponse;
import com.example.coffee.ui.viewmodel.DeviceDetailViewModel;

public class DeviceDetailActivity extends AppCompatActivity
{
    private ActivityDeviceDetailBinding binding;
    private DeviceDetailViewModel viewModel;

    private String token;
    private String serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityDeviceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 获取传入参数
        token = getIntent().getStringExtra("TOKEN");
        serialNumber = getIntent().getStringExtra("SERIAL_NUMBER");

        if (token == null || serialNumber == null)
        {
            Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }

        viewModel = new ViewModelProvider(this).get(DeviceDetailViewModel.class);

        viewModel.getDeviceDetailState().observe(this, new Observer<DeviceDetailViewModel.DeviceDetailState>() {
            @Override
            public void onChanged(DeviceDetailViewModel.DeviceDetailState state) {
                binding.progressBar.setVisibility(state == DeviceDetailViewModel.DeviceDetailState.LOADING ? View.VISIBLE : View.GONE);

                switch (state) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        DeviceDetailResponse response = viewModel.getResponse();
                        if (response != null && response.getData() != null)
                        {
                            updateUI(response.getData());
                        }
                        break;
                    case API_ERROR:
                        Toast.makeText(DeviceDetailActivity.this, "接口返回错误", Toast.LENGTH_LONG).show();
                        break;
                    case NETWORK_ERROR:
                        Toast.makeText(DeviceDetailActivity.this, "网络异常，请检查链接", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // 首次加载数据
        viewModel.getDeviceDetail(token, serialNumber);
    }

    private void updateUI(DeviceDetailResponse.Data data)
    {
        binding.tvDeviceName.setText("设备名称: " + data.getName());
        binding.tvSerialNumber.setText("序列号: " + data.getSerialNumber());
        binding.tvDeviceId.setText("设备ID: " + data.getId());
        binding.tvOrgId.setText("所属组织ID: " + data.getOrgId());
        binding.tvOperationStatus.setText("运营状态: " + parseOperationStatus(data.getOperationStatus()));
        binding.tvStatus.setText("当前状态: " + parseStatus(data.getStatus()));
    }

    private String parseOperationStatus(Integer status)
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
    private String parseStatus(Integer status)
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
