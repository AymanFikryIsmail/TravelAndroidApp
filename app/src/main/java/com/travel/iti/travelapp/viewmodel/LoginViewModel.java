package com.travel.iti.travelapp.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.view.activity.SignUpActivity;
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
    public MutableLiveData<String> UserName = new MutableLiveData<>();
    public  MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    public   MutableLiveData<User> loginData= new MutableLiveData<>();
    public   MutableLiveData<User> signUpData= new MutableLiveData<>();

    public MutableLiveData<Boolean> isSuccess =new MutableLiveData<>();

    private Context mcontext;
    public void init(Context context) {
        this.mcontext=context;
    }

//    public MutableLiveData<User> getLoginData() {
//        return loginData;
//    }

    public  MutableLiveData<User> getSignUpData(){
        if (signUpData == null) {
            signUpData = new MutableLiveData<>();
        }
        return signUpData;
    }
    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        if(view == view.findViewById(R.id.btnLogin)) {
            User loginUser = new User(EmailAddress.getValue(), Password.getValue());
            userMutableLiveData.setValue(loginUser);
        }else{
            Intent intent= new Intent(mcontext, SignUpActivity.class);
            mcontext.startActivity(intent);
        }
    }

    public  void signUpOnClick(View view){

        User loginUser = new User(EmailAddress.getValue(), Password.getValue(), UserName.getValue(), phone.getValue(),city.getValue());
        userMutableLiveData.setValue(loginUser);

    }




    public void signIn(User loginUser){
        //loginData = new MutableLiveData<>();

        Call<ApiResponse<User>> call =Apiservice.getInstance().apiRequest.SignIn(loginUser);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                        loginData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }


    public void signUp(User signUpUser) {
        //signUpData = new MutableLiveData<>();
        Call<ApiResponse<User>> signUpCall = Apiservice.getInstance().apiRequest.signup(signUpUser);
        signUpCall.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.body().status == "true" && response.body().data != null) {
                    signUpData.setValue(response.body().data);
                    isSuccess.setValue(true);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                } else {
                    isSuccess.setValue(false);
                    Toast.makeText(mcontext, "auth failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                isSuccess.setValue(false);
                Log.d("tag", "articles total result:: " + t.getMessage());
            }
        });
    }
}
