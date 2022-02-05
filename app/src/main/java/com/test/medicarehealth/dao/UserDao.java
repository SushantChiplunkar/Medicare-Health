package com.test.medicarehealth.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.test.medicarehealth.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("Select * from user_table")
    LiveData<List<User>> getUsers();


    @Query("Select * from user_table where id=:id LIMIT 1")
    LiveData<User> getUserDataFromId(int id);

    @Query("Select * from user_table where mobileNo=:mobile_no and password=:passkey")
    LiveData<User> getUserFromLogin(String mobile_no,String passkey);
}
