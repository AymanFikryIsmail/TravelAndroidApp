package com.travel.iti.travelapp.view.activity.home.fragments.favourite;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;
import com.travel.iti.travelapp.view.activity._package.FavPressCallBack;
import com.travel.iti.travelapp.view.activity.home.main.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesViewModel extends ViewModel {

    public MutableLiveData<List<PackagesPojo>> pckageList;

    public FavoritesViewModel() {
        pckageList = new MutableLiveData<>();
    }

    MainView mainView;
    public void init(MainView mainView) {
        this.mainView=mainView;
    }


    public void getFavouritePackages(int userId){
        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getFavouritePackages(userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    pckageList.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.showError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.showError("Error in connection");
            }
        });
    }



    public void setFavPackage(int pcgId ,int userId,FavPressCallBack favPressCallBack){
        Call<ApiResponse<Boolean>> call = Apiservice.getInstance().apiRequest.postFavouritePackages( userId,pcgId);
        call.enqueue(new Callback<ApiResponse<Boolean>>() {
            @Override
            public void onResponse(Call<ApiResponse<Boolean>> call, Response<ApiResponse<Boolean>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                    // isFavPressed.setValue(response.body().data);
                    favPressCallBack.setIsFav(response.body().data);
                }
                else {
                    Log.d("tag", "failed:: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Boolean>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }

}
