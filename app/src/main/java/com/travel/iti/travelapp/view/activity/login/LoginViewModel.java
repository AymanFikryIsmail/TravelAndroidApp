package com.travel.iti.travelapp.view.activity.login;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.view.activity.signup.SignUpActivity;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-13.
 */

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> EmailAddress;
    public MutableLiveData<String> Password;
    private MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<User> loginData;
    public MutableLiveData<Boolean> isSuccess ;

    LoginView loginView;
    public LoginViewModel() {
        EmailAddress = new MutableLiveData<>();
        Password = new MutableLiveData<>();
        isSuccess = new MutableLiveData<>();
        loginData = new MutableLiveData<>();

    }

    public void init(LoginView loginView) {
        this.loginView=loginView;
    }

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {
            User loginUser = new User(EmailAddress.getValue(), Password.getValue());
            userMutableLiveData.setValue(loginUser);
    }

    public void signIn(User loginUser) {
        //loginData = new MutableLiveData<>();

        Call<ApiResponse<User>> call = Apiservice.getInstance().apiRequest.SignIn(loginUser);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.body().status == "true" && response.body().data != null) {
                    loginData.setValue(response.body().data);
                    loginView.showSuccess("Signed in successfully ");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                } else {
                    loginView.showSuccess("Authentication failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                loginView.showSuccess("authentication failed : Check your network");

            }
        });
    }

//    public void googleSignIn(User loginGoogleUser){
//        Call<ApiResponse<User>> call = Apiservice.getInstance().apiRequest.googlSignIn(loginGoogleUser);
//        call.enqueue(new Callback<ApiResponse<User>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
//                if (response.body().status == "true" && response.body().data != null) {
//                    loginData.setValue(response.body().data);
//                    loginView.showSuccess("Successfully authenticated");
//                    Log.d("tag", "articles total result:: " + response.body().getMessage());
//                } else {
//                    loginView.showSuccess("authentication failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
//                Log.d("tag", "articles total result:: " + t.getMessage());
//                loginView.showSuccess("authentication failed : Check your network");
//
//            }
//        });
//    }

}
