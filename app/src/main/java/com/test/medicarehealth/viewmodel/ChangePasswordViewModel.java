package com.test.medicarehealth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.databinding.ActivityChangePasswordScreenBinding;
import com.test.medicarehealth.model.User;
import com.test.medicarehealth.repository.UserRepository;
import com.test.medicarehealth.util.KeyEnum;

import java.util.Objects;

public class ChangePasswordViewModel extends AndroidViewModel {
    private UserRepository repositoty;
    public MutableLiveData<String> newPassword = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        newPassword.setValue("");
        confirmPassword.setValue("");
        repositoty = new UserRepository(application);
    }

    public TextViewBindingAdapter.AfterTextChanged newPasswordChanged() {
        return s -> newPassword.postValue(s.toString());
    }

    public TextViewBindingAdapter.AfterTextChanged confirmPasswordChanged() {
        return s -> confirmPassword.postValue(s.toString());
    }

    public boolean isValidInfo() {
        return !newPassword.getValue().isEmpty() && !confirmPassword.getValue().isEmpty()
                && newPassword.getValue().length() > 7 && Objects.deepEquals(newPassword.getValue(), confirmPassword.getValue());
    }

    public void showError(ActivityChangePasswordScreenBinding binding) {
        if (newPassword.getValue().isEmpty())
            binding.newPasswordIL.setError("Enter password");
        else if (newPassword.getValue().length() < 8)
            binding.newPasswordIL.setError("New password should be contain at least 8 characters.");
        else binding.newPasswordIL.setError("");

        if (confirmPassword.getValue().isEmpty())
            binding.confirmPasswordIL.setError("Enter confirm password");
        else if (!Objects.deepEquals(newPassword.getValue(), confirmPassword.getValue()))
            binding.confirmPasswordIL.setError("Confirm password should be same!");
        else binding.confirmPasswordIL.setError("");


    }

    public LiveData<User> getUserFromMobileNo(String mobileNo) {
        return repositoty.getUserFromEmailOrMobileAsUsername(mobileNo);
    }

    public String changePassword(User user) {
        repositoty.update(user);
        return "OK";
    }


}
