package com.test.medicarehealth.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.MainActivity;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivityLoginScreenBinding;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.util.Utils;
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

        binding.forgotPassBtn.setOnClickListener(forgotPasswordClick);
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

    private View.OnClickListener forgotPasswordClick = view -> {
        Intent otpScreenCall = new Intent(LoginScreen.this,OTPVerificationScreen.class);
        startActivity(otpScreenCall);

        /*loginViewModel.userExistOrNot().observe(this,user -> {
            if (user!=null){
                showForgotPasswordPin();
            }
        });*/
    };

    private void showForgotPasswordPin() {



        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("App OTP Notification","All Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        int otp = Utils.getOtp();

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this,"App OTP Notification")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Forgot password OTP")
                .setContentText("Password OTP is "+ Utils.getOtp()+". Use this to reset or change password")
                .setAutoCancel(true);


        /*Intent notificationIntent = new Intent(this, ChangePasswordScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nb.setContentIntent(contentIntent);*/

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1,nb.build());

        VerificationOTPDialog dialog = new VerificationOTPDialog();
        dialog.show(getSupportFragmentManager(),"OTP_SEND");
    }

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