package com.travel.iti.travelapp.view.activity.signup;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-26.
 */

public class SignUpViewModel extends ViewModel {


    public MutableLiveData<String> EmailAddress;
    public MutableLiveData<String> Password;
    public MutableLiveData<String> UserName;
    public MutableLiveData<String> phone;
    public MutableLiveData<String> city;
    private MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<User> signUpData;
    public MutableLiveData<Boolean> isSuccess;

    private Context mcontext;
    private PrefManager prefManager;

    private SignUpView view;
    public SignUpViewModel() {
        EmailAddress = new MutableLiveData<>();
        Password = new MutableLiveData<>();
        UserName = new MutableLiveData<>();
        phone = new MutableLiveData<>();
        city = new MutableLiveData<>();
        signUpData = new MutableLiveData<>();
        isSuccess = new MutableLiveData<>();
    }

    public void init(Context context) {
        this.mcontext = context;
        this.view= (SignUpView) context;
        prefManager=new PrefManager(context);
    }

    public MutableLiveData<User> getSignUpData() {
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

        if (view == view.findViewById(R.id.btnLogin)) {
            User loginUser = new User(EmailAddress.getValue(), Password.getValue());
            userMutableLiveData.setValue(loginUser);
        } else {
            Intent intent = new Intent(mcontext, SignUpActivity.class);
            mcontext.startActivity(intent);
        }
    }

    public void signUpOnClick(View view) {

        User loginUser = new User(EmailAddress.getValue(), Password.getValue(), UserName.getValue(), phone.getValue(), city.getValue());
        userMutableLiveData.setValue(loginUser);

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
                    prefManager.setUserId(response.body().data.getId());
                    prefManager.setUserData(response.body().data);;
                    view.showSuccess("Successfully authenticated");

                } else {
                    isSuccess.setValue(false);
                    view.shwoError("auth failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                isSuccess.setValue(false);
                view.shwoError(t.getMessage());
                view.shwoError("auth failed : check your network");
                Log.d("tag", "articles total result:: " + t.getMessage());
            }
        });
    }
}
