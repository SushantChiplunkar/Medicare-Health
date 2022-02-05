package com.test.medicarehealth.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.MainActivity;
import com.test.medicarehealth.R;
import com.test.medicarehealth.util.KeyEnum;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Prefs.getBoolean(KeyEnum.IS_LOGIN.toString(), false)) {
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}