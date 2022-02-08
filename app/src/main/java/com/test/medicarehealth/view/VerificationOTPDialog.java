package com.test.medicarehealth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.OtpVerificationDialogBinding;

public class VerificationOTPDialog extends DialogFragment {
    private OtpVerificationDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.otp_verification_dialog, container, false);
        binding.setLifecycleOwner(getActivity());
        this.setCancelable(false);
        View view = binding.getRoot();
        return view;
    }
}
