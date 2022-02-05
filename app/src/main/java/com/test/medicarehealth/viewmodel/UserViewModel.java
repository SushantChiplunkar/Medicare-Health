package com.test.medicarehealth.viewmodel;

import android.app.Application;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.medicarehealth.databinding.ActivitySignupScreenBinding;
import com.test.medicarehealth.model.User;
import com.test.medicarehealth.repository.UserRepository;

import java.util.List;
import java.util.regex.Pattern;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public MutableLiveData<String> firstName = new MutableLiveData<>();
    public MutableLiveData<String> lastName = new MutableLiveData<>();
    public MutableLiveData<String> mobileNo = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        firstName.setValue("");
        lastName.setValue("");
        mobileNo.setValue("");
        email.setValue("");
        userName.setValue("");
        password.setValue("");
        userRepository = new UserRepository(application);
    }

    public TextViewBindingAdapter.AfterTextChanged firstNameChange(){
        return s ->firstName.postValue(s.toString());
    }

    public LiveData<String> firstNameLiveData(){
        return firstName;
    }

    public TextViewBindingAdapter.AfterTextChanged lastNameChange(){
        return s ->lastName.postValue(s.toString());
    }

    public LiveData<String> lastNameLiveData(){
        return lastName;
    }

    public TextViewBindingAdapter.AfterTextChanged mobileNoChange(){
        return s ->mobileNo.postValue(s.toString());
    }

    public LiveData<String> mobileNoLiveData(){
        return mobileNo;
    }

    public TextViewBindingAdapter.AfterTextChanged emailChange(){
        return s ->email.postValue(s.toString());
    }

    public LiveData<String> emailLiveData(){
        return email;
    }

    public TextViewBindingAdapter.AfterTextChanged userNameChange(){
        return s -> userName.postValue(s.toString());
    }

    public TextViewBindingAdapter.AfterTextChanged passwordChange(){
        return s -> password.postValue(s.toString());
    }

    public LiveData<String> passwordLiveData(){
        return password;
    }

    public LiveData<List<User>> getusers(){
        return userRepository.getUsers();
    }

    public boolean isValid(){
        boolean isValid = false;
        if(!firstName.getValue().isEmpty()&&!lastName.getValue().isEmpty()&&!mobileNo.getValue().isEmpty()&& Pattern.matches("^[+]?[0-9]{10,13}$",mobileNo.getValue()))
            isValid = true;
        if (!email.getValue().isEmpty()){
            isValid = Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches();
        }
        if (!password.getValue().isEmpty() && password.getValue().length()>8)
            isValid = true;

        return isValid;
    }

    public void showErrors(ActivitySignupScreenBinding binding){
        if (firstName.getValue().isEmpty())
            binding.firstNameTIL.setError("Enter first name");
        if (lastName.getValue().isEmpty())
            binding.lastNameTIL.setError("Enter last name");
        if (mobileNo.getValue().isEmpty())
            binding.phoneNoTIL.setError("Enter mobile number");
        else if (!PhoneNumberUtils.isGlobalPhoneNumber(mobileNo.getValue()))
            binding.phoneNoTIL.setError("Invalid mobile number");
        if(password.getValue().isEmpty())
            binding.passwordTIL.setError("Enter password");

        if(!email.getValue().isEmpty())
            if (!Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches())
                binding.emailTIL.setError("Invalid email address");
    }


    public LiveData<String> addUser() {
        MutableLiveData<String> result = new MutableLiveData<>();
        User user = new User(firstName.getValue(),lastName.getValue(),mobileNo.getValue()
                ,email.getValue().isEmpty()?"":email.getValue(),email.getValue().isEmpty()?mobileNo.getValue():email.getValue(),password.getValue());

        userRepository.insert(user);
        result.setValue("OK");
        return result;
    }
}
