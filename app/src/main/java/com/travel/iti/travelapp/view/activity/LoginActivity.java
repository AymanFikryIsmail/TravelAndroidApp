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
import com.travel.iti.travelapp.databinding.ActivityLoginBinding;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.viewmodel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(this);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);

        loginViewModel.loginData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User loginUser) {

                if (TextUtils.isEmpty(loginUser.getEmail())) {
                    binding.txtEmailAddress.setError("Enter an E-Mail Address");
                    binding.txtEmailAddress.requestFocus();
                }
//                else if (!loginUser.isEmailValid()) {
//                    binding.txtEmailAddress.setError("Enter a Valid E-mail Address");
//                    binding.txtEmailAddress.requestFocus();
//                }
                else if (TextUtils.isEmpty(loginUser.getPassword())) {
                    binding.txtPassword.setError("Enter a Password");
                    binding.txtPassword.requestFocus();
                }
//                else if (!loginUser.isPasswordLengthGreaterThan5()) {
//                    binding.txtPassword.setError("Enter at least 6 Digit password");
//                    binding.txtPassword.requestFocus();
//                }
                else {

//                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                    loginViewModel.signIn(loginUser);
                }

            }
        });
    }


}
