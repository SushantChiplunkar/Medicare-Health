package com.test.medicarehealth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.medicarehealth.databinding.ActivityLoginScreenBinding;
import com.test.medicarehealth.model.User;
import com.test.medicarehealth.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userName.setValue("");
        password.setValue("");
        userRepository = new UserRepository(application);
    }

    public TextViewBindingAdapter.AfterTextChanged userNameChanged(){
        return s -> userName.postValue(s.toString());
    }

    public TextViewBindingAdapter.AfterTextChanged passwordChanged(){
        return s -> password.postValue(s.toString());
    }

    public boolean isValidCredentials(){
        return !userName.getValue().isEmpty() && !password.getValue().isEmpty();
    }

    public void showCredentialError(ActivityLoginScreenBinding binding){
        if (userName.getValue().isEmpty())
            binding.userNameIL.getEditText().setError("Enter user name");
        if (password.getValue().isEmpty())
            binding.passwordIL.getEditText().setError("Enter password");
    }

    public LiveData<User> doLogin(){
        return userRepository.getUserInfoFromLogin(userName.getValue(),password.getValue());
    }
}
