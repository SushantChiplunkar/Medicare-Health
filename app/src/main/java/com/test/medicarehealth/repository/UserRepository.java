package com.test.medicarehealth.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.medicarehealth.config.AppDatabase;
import com.test.medicarehealth.dao.UserDao;
import com.test.medicarehealth.model.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private MutableLiveData<User> userData = new MutableLiveData<>();
    private LiveData<List<User>> users;

    public UserRepository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
        users = userDao.getUsers();
    }

    public LiveData<User> getUserInfoFromLogin(String userName,String passKey){
        return userDao.getUserFromLogin(userName,passKey);
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    public void insert(User user) {
        new InsertUser(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUser(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUser(userDao).execute(user);
    }

    public static class InsertUser extends AsyncTask<User, Void, String> {
        private UserDao userDao;

        public InsertUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected String doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return "Done";
        }
    }

    public static class UpdateUser extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public UpdateUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    public static class DeleteUser extends AsyncTask<User,Void,Void>{
        private UserDao userDao;

        public DeleteUser(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }
}
