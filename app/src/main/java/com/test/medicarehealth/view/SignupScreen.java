package com.test.medicarehealth.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.test.medicarehealth.MainActivity;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivitySignupScreenBinding;
import com.test.medicarehealth.model.User;
import com.test.medicarehealth.viewmodel.UserViewModel;

public class SignupScreen extends AppCompatActivity {
    private ActivitySignupScreenBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup_screen);
        binding.setLifecycleOwner(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setUserviewmodel(userViewModel);
        //binding..setOnClickListener(onResetButtonClick);
        binding.signupBtn.setOnClickListener(onSignUpButtonClick);
        setSupportActionBar(binding.toolbarLayout.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userViewModel.firstNameLiveData().observe(this,s -> {
            if (s.length()>0)
                binding.firstNameTIL.setError("");
            else binding.firstNameTIL.setError("Enter first name");
        });

        userViewModel.lastNameLiveData().observe(this,s -> {
            if (s.length()>0)
                binding.lastNameTIL.setError("");
            else binding.lastNameTIL.setError("Enter last name");
        });

        userViewModel.mobileNoLiveData().observe(this,s -> {
            if (s.length()>0)
                binding.phoneNoTIL.setError("");
            else binding.phoneNoTIL.setError("Enter contact number");
        });

        userViewModel.emailLiveData().observe(this,s -> {
            if (s.length()>0 && Patterns.EMAIL_ADDRESS.matcher(s).matches())
                binding.emailTIL.setError("");
            else binding.emailTIL.setError("Invalid email");
        });

        userViewModel.passwordLiveData().observe(this,s -> {
            if (s.length()>0)
                binding.passwordTIL.setError("");
            else binding.passwordTIL.setError("Enter password");
        });
    }

    private View.OnClickListener onResetButtonClick = view -> {
        resetValues();
    };

    private void resetValues() {
        binding.firstNameTIL.getEditText().setText("");
        binding.lastNameTIL.getEditText().setText("");
        binding.phoneNoTIL.getEditText().setText("");
        binding.emailTIL.getEditText().setText("");
        binding.passwordTIL.getEditText().setText("");
        binding.firstNameTIL.requestFocus();
    }

    private View.OnClickListener onSignUpButtonClick = view -> {
        if (userViewModel.isValid())
            userViewModel.addUser().observe(this,s -> {
                if(!s.isEmpty() && s.equalsIgnoreCase("OK")) {
                    showToast();
                }
                else showError();
            });
        else userViewModel.showErrors(binding);
    };

    private void showError() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    private void showToast() {
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,LoginScreen.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToMain();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }


}