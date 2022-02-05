package com.test.medicarehealth;



import android.app.Application;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.util.KeyEnum;

public class MedicalHealthApplication extends Application implements SessionService{


    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

    }

    @Override
    public boolean isUserLoggedIn() {
        return Prefs.getBoolean(KeyEnum.IS_LOGIN.toString(),false);
    }
}
