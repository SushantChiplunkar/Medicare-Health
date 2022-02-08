package com.test.medicarehealth.viewmodel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.R;
import com.test.medicarehealth.model.User;
import com.test.medicarehealth.repository.UserRepository;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.util.Utils;

public class OTPVerificationViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public MutableLiveData<String> mobileNoEnter = new MutableLiveData<>();
    public MutableLiveData<String> otpEnter = new MutableLiveData<>();

    public OTPVerificationViewModel(@NonNull Application application) {
        super(application);
        mobileNoEnter.setValue("");
        userRepository = new UserRepository(application);
    }

    public TextViewBindingAdapter.AfterTextChanged mobileNoTextChanged(){
        return s -> mobileNoEnter.postValue(s.toString());
    }

    public TextViewBindingAdapter.AfterTextChanged otpAfterTextChanged(){
        return s -> otpEnter.postValue(s.toString());
    }

    public LiveData<User> userExistOrNot() {
        return userRepository.getUserFromEmailOrMobileAsUsername(mobileNoEnter.getValue());
    }

    public void showForgotPasswordPin() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("App OTP Notification","All Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getApplication().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        int otp = Utils.getOtp();
        Prefs.putInt(KeyEnum.OTP.toString(),otp);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(getApplication().getApplicationContext(),"App OTP Notification")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Forgot password OTP")
                .setContentText("Password OTP is "+ otp +".")
                .setAutoCancel(true);
        /*Intent notificationIntent = new Intent(this, ChangePasswordScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nb.setContentIntent(contentIntent);*/
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplication().getApplicationContext());
        managerCompat.notify(1,nb.build());
    }

}
