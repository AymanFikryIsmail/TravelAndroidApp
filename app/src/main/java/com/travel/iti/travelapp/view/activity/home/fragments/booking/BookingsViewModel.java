package com.travel.iti.travelapp.view.activity.home.fragments.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsViewModel extends ViewModel {

    public MutableLiveData<List<PackagesPojo>> pckageList;

    public BookingsViewModel() {
        pckageList = new MutableLiveData<>();
    }


    public void getFavouritePackages(int userId){
        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getFavouritePackages(userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    pckageList.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Log.d("tag", "failed:: " + response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }

}
