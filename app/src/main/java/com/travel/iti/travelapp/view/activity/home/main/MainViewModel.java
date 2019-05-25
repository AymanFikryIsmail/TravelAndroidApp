package com.travel.iti.travelapp.view.activity.home.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public   MutableLiveData<List<CityPackage>> cityPackageData = new MutableLiveData<>();

    private Context mcontext;
    public void init(Context context) {
        this.mcontext=context;
    }
//    public MainViewModel(Context context) {
//        this.mcontext=context;
//    }
//    public MutableLiveData<List<CityPackage>> getCityPackageData() {
//        if (cityPackageData == null) {
//            cityPackageData = new MutableLiveData<>();
//        }
//        return cityPackageData;
//    }
    public void getData(){
        Call<ApiResponse<List<CityPackage>>> call = Apiservice.getInstance().apiRequest.getPackageCity();
        call.enqueue(new Callback<ApiResponse<List<CityPackage>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CityPackage>>> call, Response<ApiResponse<List<CityPackage>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    cityPackageData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<CityPackage>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }
}
