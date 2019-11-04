package com.travel.iti.travelapp.view.activity.signup;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivitySignUpBinding;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.view.activity.home.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class SignUpActivity extends AppCompatActivity implements SignUpView {
    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;
    private int cityId;
    private List<CityPackage> cityPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        signUpViewModel.init(this);
        binding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);
        binding.setLifecycleOwner(this);
        binding.setActivity(this);
        binding.setSignUpViewModel(signUpViewModel);
        cityPackages = new ArrayList<>();
        //////////////
        signUpViewModel.getCities();
        signUpViewModel.getCityPackage().observe(this, new Observer<List<CityPackage>>() {
            @Override
            public void onChanged(@Nullable List<CityPackage> cityPackageList) {
                cityPackages = cityPackageList;
                List<String> cityList = new ArrayList<>(cityPackageList.size());
                for (CityPackage cityPackage : cityPackageList) {
                    cityList.add(cityPackage.getCityName());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SignUpActivity.this,
                        android.R.layout.simple_spinner_item, cityList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.selectTextCity.setAdapter(dataAdapter);
            }
        });
        binding.selectTextCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (cityPackages.size() != 0)
                    cityId = cityPackages.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        signUpViewModel.getUser().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(@Nullable User signUpUser) {
//
//
//            }
//        });
//        binding.editTextCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
//                startActivityForResult(intent, 11);
//            }
//        });
        signUpViewModel.isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSuccess) {
                if (isSuccess) {
                    binding.progressView.setVisibility(View.GONE);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public  void signUp(){
        User signUpUser=new User( binding.editTextUser.getText().toString() ,binding.editTextPassword.getText().toString()
                , binding.editTextPhone.getText().toString() , cityId);
        if (TextUtils.isEmpty(signUpUser.getUsername())) {
            binding.editTextUser.setError(getString(R.string.input_error_name));
            binding.editTextUser.requestFocus();
        } else if (TextUtils.isEmpty(signUpUser.getPassword())) {
            binding.editTextPassword.setError(getString(R.string.input_error_password));
            binding.editTextPassword.requestFocus();
        } else if (signUpUser.getPassword().length() < 6) {
            binding.editTextPassword.setError(getString(R.string.input_error_password_length));
            binding.editTextPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(signUpUser.getPhone())) {
            binding.editTextPhone.setError(getString(R.string.input_error_phone));
            binding.editTextPhone.requestFocus();
        } else if (signUpUser.getPhone().length() != 11) {
            binding.editTextPhone.setError(getString(R.string.input_error_phone_invalid));
            binding.editTextPhone.requestFocus();
        } else {
            binding.progressView.setVisibility(View.VISIBLE);
            signUpViewModel.signUp(signUpUser);
        }
    }


    @Override
    public void showSuccess(String success) {
        binding.progressView.setVisibility(View.GONE);

        Toast.makeText(this, success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void shwoError(String error) {
        binding.progressView.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && data != null) {
            if (resultCode == Activity.RESULT_OK) {
                String addrs = data.getStringExtra("address").split(" ")[0];
//                binding.editTextCity.setText(TextUtils.isEmpty(addrs)?"":addrs);
            } else {
                Toast.makeText(SignUpActivity.this, "open gps and try again ", Toast.LENGTH_LONG).show();

            }
        }
    }
}
