package com.test.medicarehealth.config;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.test.medicarehealth.R;
import com.test.medicarehealth.dao.UserDao;
import com.test.medicarehealth.model.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract UserDao userDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance==null){
            instance = Room.databaseBuilder(context,AppDatabase.class, R.string.app_name+"_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
