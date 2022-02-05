package com.test.medicarehealth.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.MainActivity;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivityLoginScreenBinding;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.viewmodel.LoginViewModel;

public class LoginScreen extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_screen);
        binding.setLifecycleOwner(this);

        binding.registerBtn.setOnClickListener(view -> startActivity(new Intent(this,SignupScreen.class)));
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        binding.signInBtn.setOnClickListener(signInBtnClick);
        setSupportActionBar(binding.toolbarLayout.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //binding.toolbarLayout.toolbar.setOnClickListener(view -> finish());
    }

    private View.OnClickListener signInBtnClick = view -> {
        if (loginViewModel.isValidCredentials()){
            loginViewModel.doLogin().observe(this,user -> {
                if (user!=null){
                    Prefs.putBoolean(KeyEnum.IS_LOGIN.toString(),true);
                    startActivity(new Intent(this,HomeScreen.class));
                    finish();
                }else Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            });
        }else loginViewModel.showCredentialError(binding);
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToMain();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}