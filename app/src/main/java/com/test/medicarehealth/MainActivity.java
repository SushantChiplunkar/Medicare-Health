package com.test.medicarehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.medicarehealth.databinding.ActivityMainBinding;
import com.test.medicarehealth.view.LoginScreen;
import com.test.medicarehealth.view.SignupScreen;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.loginBtnHome.setOnClickListener(LoginButtonClick);
        binding.createAccountTV.setOnClickListener(createAccountBtnClick);

    }

    private View.OnClickListener LoginButtonClick = view -> {
        startActivity(new Intent(this,LoginScreen.class));
        finish();
    };

    private View.OnClickListener createAccountBtnClick = view -> {
        startActivity(new Intent(this, SignupScreen.class));
        finish();
    };
}