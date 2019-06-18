package com.travel.iti.travelapp.view.activity.home.fragments.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;
import com.travel.iti.travelapp.view.activity.home.main.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsViewModel extends ViewModel {

    public MutableLiveData<List<BookedPackage>> pckageList;

    public BookingsViewModel() {
        pckageList = new MutableLiveData<>();
    }


    MainView mainView;
    public void init(MainView mainView) {
        this.mainView=mainView;
    }

    public void getBookedPackages(int userId){
        Call<ApiResponse<List<BookedPackage>>> call = Apiservice.getInstance().apiRequest.getBookedPackages(userId);
        call.enqueue(new Callback<ApiResponse<List<BookedPackage>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BookedPackage>>> call, Response<ApiResponse<List<BookedPackage>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    pckageList.setValue(response.body().data);
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.showError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<BookedPackage>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.showError("Error in connection");
            }
        });
    }

}
