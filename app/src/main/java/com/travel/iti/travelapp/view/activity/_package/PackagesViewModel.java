package com.travel.iti.travelapp.view.activity._package;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;
import com.travel.iti.travelapp.view.activity.home.main.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesViewModel extends ViewModel {

    public MutableLiveData<List<PackagesPojo>> packagesData ;
    public MutableLiveData<Boolean> isFavPressed ;


    public PackagesViewModel() {
        packagesData = new MutableLiveData<>();
        isFavPressed = new MutableLiveData<>();

    }

    MainView mainView;
    public void init(MainView mainView) {
        this.mainView=mainView;
    }


    public void setFavPackage(int pcgId , int userId,FavPressCallBack favPressCallBack){
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
                    //Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Boolean>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }

    public void getData(String city , int userId){
        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getPackage(city , userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.shwoError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.shwoError("Error in connection");
            }
        });
    }

    public void getRecentPackages(int userId){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getRecentPackages(userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.shwoError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.shwoError("Error in connection");
            }
        });

    }

    public void getRecommendedPackages( int userId){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getRecommendedPackages (userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.shwoError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.shwoError("Error in connection");
            }
        });

    }

    public void getAllOffesrsPackages (int userId){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getAllOffesrsPackages (userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.shwoError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.shwoError("Error in connection");
            }
        });

    }

    public void getHomeSearchedPackages (int userId){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getHomeSearchedPackages (userId);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    mainView.showSuccess("");
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    mainView.shwoError("Error in connection");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());
                mainView.shwoError("Error in connection");
            }
        });

    }


}
