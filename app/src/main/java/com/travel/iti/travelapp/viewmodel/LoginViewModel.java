package com.travel.iti.travelapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-13.
 */

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
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
    public void signIn(){
        final MutableLiveData<User> data = new MutableLiveData<>();

        Call<ApiResponse<User>> call =Apiservice.getInstance().apiRequest.SignIn(new User("",""));
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.body().status != "true"&&response.body().data!=null  ) {
                        data.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

            }
        });
    }
}
