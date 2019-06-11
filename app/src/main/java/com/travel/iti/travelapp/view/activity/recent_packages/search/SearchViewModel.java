package com.travel.iti.travelapp.view.activity.recent_packages.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    public MutableLiveData<List<CityPackage>> searchedCity;
    private Context mcontext;
    private PrefManager prefManager;

    public SearchViewModel() {
        searchedCity = new MutableLiveData<>();
    }
    public void init(Context context) {

        this.mcontext=context;
    }

    public void getSearchedCities(){
        Call<ApiResponse<List<CityPackage>>> call = Apiservice.getInstance().apiRequest.getSearchedCity();
        call.enqueue(new Callback<ApiResponse<List<CityPackage>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CityPackage>>> call, Response<ApiResponse<List<CityPackage>>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    searchedCity.setValue(response.body().data);
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


