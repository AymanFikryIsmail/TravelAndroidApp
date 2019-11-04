package com.travel.iti.travelapp.view.activity.signup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-26.
 */

public class SignUpViewModel extends ViewModel {


    private MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<User> signUpData;
    public MutableLiveData<Boolean> isSuccess;
    public MutableLiveData<List<CityPackage>> cityPackageMutableLiveData;

    private Context mcontext;
    private PrefManager prefManager;

    private SignUpView view;
    public SignUpViewModel() {

        signUpData = new MutableLiveData<>();
        isSuccess = new MutableLiveData<>();
        cityPackageMutableLiveData = new MutableLiveData<>();
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
    public MutableLiveData<List<CityPackage>> getCityPackage() {
        if (cityPackageMutableLiveData == null) {
            cityPackageMutableLiveData = new MutableLiveData<>();
        }
        return cityPackageMutableLiveData;
    }
    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

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
                    view.showSuccess("Signed up successfully ");
                } else {
                    isSuccess.setValue(false);
                    view.shwoError("Authentication failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                isSuccess.setValue(false);
                view.shwoError(t.getMessage());
                view.shwoError("Authentication failed . plesae, check your network");
                Log.d("tag", "articles total result:: " + t.getMessage());
            }
        });
    }
    public void getCities(){
        Call<ApiResponse<List<CityPackage>>> call = Apiservice.getInstance().apiRequest.getPackageCity();
        call.enqueue(new Callback<ApiResponse<List<CityPackage>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CityPackage>>> call, Response<ApiResponse<List<CityPackage>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    cityPackageMutableLiveData.setValue(response.body().data);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<CityPackage>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
            }
        });
    }
}
