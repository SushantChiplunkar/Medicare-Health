package com.test.medicarehealth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivityOtpverificationScreenBinding;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.util.Utils;
import com.test.medicarehealth.viewmodel.OTPVerificationViewModel;

public class OTPVerificationScreen extends AppCompatActivity {
    private ActivityOtpverificationScreenBinding binding;
    private OTPVerificationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otpverification_screen);
        binding.setLifecycleOwner(this);
        /*if (!isOTPSendViewVisible())
        showOTPSendView();*/

        viewModel = new ViewModelProvider(this).get(OTPVerificationViewModel.class);
        binding.setMViewModel(viewModel);
        binding.getOTPBtn.setOnClickListener(getOtpClick);
        binding.verifyOTPBtn.setOnClickListener(verifyOTP);

        setSupportActionBar(binding.otpScreenToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private boolean isOTPSendViewVisible() {
        return binding.otpSend.getVisibility()==View.VISIBLE && binding.otpReceive.getVisibility()==View.GONE;
    }

    private View.OnClickListener getOtpClick = view -> {
        if (!viewModel.mobileNoEnter.getValue().isEmpty()) {
            viewModel.userExistOrNot().observe(this,user -> {
                if (user!=null){
                    Prefs.putString(KeyEnum.USER_MOBILE_NO.toString(),user.getMobileNo());
                    Prefs.putInt(KeyEnum.USER_ID.toString(),user.getId());
                    viewModel.showForgotPasswordPin();
                    showOTPReceiveView();
                }else
                    Toast.makeText(this, "No user exist for this number", Toast.LENGTH_SHORT).show();
            });
        }
    };

    private View.OnClickListener verifyOTP = view -> {
        int otp = Prefs.getInt(KeyEnum.OTP.toString(),0);
        if (otp!=0 && otp==Integer.parseInt(viewModel.otpEnter.getValue())){
            startActivity(new Intent(this,ChangePasswordScreen.class));
        }
    };

    private void showOTPSendView(){
        binding.otpSend.setVisibility(View.VISIBLE);
        binding.otpReceive.setVisibility(View.GONE);
    }

    private void showOTPReceiveView(){
        binding.otpReceive.setVisibility(View.VISIBLE);
        binding.otpSend.setVisibility(View.GONE);
    }


}