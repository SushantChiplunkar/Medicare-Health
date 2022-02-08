package com.test.medicarehealth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.auth.User;
import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivityChangePasswordScreenBinding;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.viewmodel.ChangePasswordViewModel;

public class ChangePasswordScreen extends AppCompatActivity {
    private ActivityChangePasswordScreenBinding binding;
    private ChangePasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password_screen);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        binding.setViewModel(viewModel);

        binding.changePasswordBtn.setOnClickListener(changePasswordClicked);

    }

    private View.OnClickListener changePasswordClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (viewModel.isValidInfo()){
                viewModel.getUserFromMobileNo(Prefs.getString(KeyEnum.USER_MOBILE_NO.toString(),"")).observe(ChangePasswordScreen.this, user1 -> {
                    user1.setPassword(binding.newPasswordIL.getEditText().getText().toString());
                  String s = viewModel.changePassword(user1);
                  if (s.length()!=0)
                      startActivity(new Intent(ChangePasswordScreen.this,HomeScreen.class));
                        finish();
                });

            }else viewModel.showError(binding);
        }
    };


}