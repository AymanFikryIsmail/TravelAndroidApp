package com.travel.iti.travelapp.view.activity.home.fragments.favourite;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesViewModel extends ViewModel {

    public MutableLiveData<List<PackagesPojo>> pckageList;
    private Context mcontext;
    public FavoritesViewModel() {
        pckageList = new MutableLiveData<>();
    }

    public void init(Context context) {
        this.mcontext=context;
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
                    Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<PackagesPojo>>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }
}
