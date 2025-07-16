package com.example.coffee.ui;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffee.databinding.ActivityDeviceDetailBinding;
import com.example.coffee.databinding.ActivityLoginBinding;
import com.example.coffee.model.response.LoginResponse;
import com.example.coffee.ui.viewmodel.LoginViewModel;
import com.example.coffee.ui.viewmodel.LoginViewModel.LoginState;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: 默认值填入(仅开发环境使用)
        {
            binding.etAccount.setText("13609450394");
            binding.etPassword.setText("htyj-coffee");
        }

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 观察登录状态变化
        viewModel.getLoginState().observe(this, state -> {
            binding.progress.setVisibility(
                    state == LoginState.LOADING ? View.VISIBLE : View.GONE
            );

            binding.btnLogin.setEnabled(state != LoginState.LOADING);

            LoginResponse response = viewModel.getResponse();

            switch (state) {
                case SUCCESS:
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this, MainActivity.class));

//                    Intent intent = new Intent(this, DeviceDetailActivity.class);
//                    intent.putExtra("TOKEN", response.getData());
//                    // 后面改为统一从上位机获取serialNumber
//                    intent.putExtra("SERIAL_NUMBER", "M120210006");

                    // 存储到本地
                    SharedPreferences sp = getSharedPreferences("app_prefs", MODE_PRIVATE);
                    sp.edit()
                            .putString("TOKEN", response.getData())
                            .putString("SERIAL_NUMBER", "M120210006")
                            .apply();
                    startActivity(new Intent(this, DeviceDetailActivity.class));
                    finish();
                    break;
                case API_ERROR:
//                    Toast.makeText(this, "账号或密码错误", Toast.LENGTH_LONG).show();
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(this, "网络错误，请重试", Toast.LENGTH_LONG).show();
                    break;
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String account = binding.etAccount.getText().toString();
            String password = binding.etPassword.getText().toString();
            viewModel.login(account, password);
        });
    }
}
