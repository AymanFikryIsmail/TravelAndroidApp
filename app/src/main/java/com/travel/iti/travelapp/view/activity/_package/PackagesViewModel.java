package com.travel.iti.travelapp.view.activity._package;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagesViewModel extends ViewModel {

    public MutableLiveData<List<PackagesPojo>> packagesData = new MutableLiveData<>();

    private Context mcontext;
    public void init(Context context) {
        this.mcontext=context;
    }

    public void getData(String city){
        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getPackage(city);
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }

    public void getRecentPackages(){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getRecentPackages();
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Toast.makeText(mcontext,"faild", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });

    }

    public void getRecommendedPackages(){

        Call<ApiResponse<List<PackagesPojo>>> call = Apiservice.getInstance().apiRequest.getRecommendedPackages ();
        call.enqueue(new Callback<ApiResponse<List<PackagesPojo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PackagesPojo>>> call, Response<ApiResponse<List<PackagesPojo>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    packagesData.setValue(response.body().data);
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    Toast.makeText(mcontext,"faild", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });

    }
}
