package com.travel.iti.travelapp.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivitySignUpBinding;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    private SignUpViewModel signUpViewModel;

    private ActivitySignUpBinding binding2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding2 = DataBindingUtil.setContentView(SignUpActivity.this,R.layout.activity_sign_up);
        binding2.setLifecycleOwner(this);


        signUpViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User signUpUser) {

                if (TextUtils.isEmpty(signUpUser.getEmail())) {
                    binding2.editTextEmail.setError("Enter an E-Mail Address");
                    binding2.editTextEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(signUpUser.getPassword())) {
                    binding2.editTextPassword.setError("Enter a Password");
                    binding2.editTextPassword.requestFocus();
                }

                else if (TextUtils.isEmpty(signUpUser.getPassword())) {
                    binding2.editTextPassword2.setError("Enter a Password");
                    binding2.editTextPassword2.requestFocus();
                }


                else if (TextUtils.isEmpty(signUpUser.getUsername())) {
                    binding2.editTextUser.setError("Enter a User Name");
                    binding2.editTextUser.requestFocus();
                }

                else {

                    Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
