package com.travel.iti.travelapp.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivitySignUpBinding;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.viewmodel.LoginViewModel;

import static android.support.constraint.ConstraintSet.VISIBLE;

public class SignUpActivity extends AppCompatActivity {
    private LoginViewModel signUpViewModel;

    private ActivitySignUpBinding binding2;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        signUpViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        signUpViewModel.init(this);
        binding2 = DataBindingUtil.setContentView(SignUpActivity.this,R.layout.activity_sign_up);
        binding2.setLifecycleOwner(this);
        binding2.setSignUpViewModel(signUpViewModel);


        signUpViewModel.UserName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(SignUpActivity.this,"user name"+ s, Toast.LENGTH_LONG).show();

            }
        });

//        signUpViewModel.getSignUpData().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(@Nullable User user) {
//               // Toast.makeText(SignUpActivity.this,"isSuccess : "+ isSuccess, Toast.LENGTH_LONG).show();
//                frameLayout.setVisibility(View.GONE);
//                Intent gotoHome = new Intent(SignUpActivity.this,MainActivity.class);
//                startActivity(gotoHome);
//            }
//        });

        signUpViewModel.getUser().observe(this, new Observer<User>() {



            @Override
            public void onChanged(@Nullable User signUpUser) {

               // binding2.progressView.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(signUpUser.getUsername())) {
                    binding2.editTextUser.setError("Enter a User Name");
                    binding2.editTextUser.requestFocus();
                }

                else if (TextUtils.isEmpty(signUpUser.getEmail())) {
                    binding2.editTextEmail.setError("Enter an E-Mail Address");
                    binding2.editTextEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(signUpUser.getPassword())) {
                    binding2.editTextPassword.setError("Enter a Password");
                    binding2.editTextPassword.requestFocus();
                }

                else if (TextUtils.isEmpty(signUpUser.getPhone())) {
                    binding2.editTextPhone.setError("Enter a Phone");
                    binding2.editTextPhone.requestFocus();
                }

                else if (TextUtils.isEmpty(signUpUser.getCity())) {
                    binding2.editTextCity.setError("Enter a City Name");
                    binding2.editTextCity.requestFocus();
                }

                else {

//                    Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
//                    startActivity(intent);
                    binding2.progressView.setVisibility(View.VISIBLE);

                    signUpViewModel.signUp(signUpUser);


                }

            }
        });
       signUpViewModel.isSuccess.observe(this, new Observer<Boolean>() {
    @Override
    public void onChanged(@Nullable Boolean isSuccess) {

        Toast.makeText(SignUpActivity.this,"isSuccess : "+ isSuccess, Toast.LENGTH_LONG).show();
        binding2.progressView.setVisibility(View.GONE);
        Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

    }
});

    }



}
