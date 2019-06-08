package com.travel.iti.travelapp.view.activity.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;
import com.travel.iti.travelapp.view.activity._package.FavPressCallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-29.
 */

public class BookingViewModel extends ViewModel {

    public MutableLiveData<Integer> noOfAdults;
    public MutableLiveData<Integer> noOfChilds;
    public MutableLiveData<Double> totalCost;

    private double price;
    public BookingViewModel() {
        noOfAdults=new MutableLiveData<>();
        noOfChilds=new MutableLiveData<>();
        totalCost=new MutableLiveData<>();

        noOfAdults.setValue(0);
        noOfChilds.setValue(0);
        totalCost.setValue(0.0);

    }

    public void init(double price ){
        this.price=price;
    }

    public void incrementAdults(View v){
        noOfAdults.setValue(noOfAdults.getValue()+1);
        totalCost.setValue((noOfChilds.getValue()+noOfAdults.getValue())*price);
    }
    public void decrementAdults(View v){
        if (noOfAdults.getValue()>0) {
            noOfAdults.setValue(noOfAdults.getValue() - 1);
            totalCost.setValue((noOfChilds.getValue()+noOfAdults.getValue())*price);

        }
    }
    public void incrementChilds(View v){
        noOfChilds.setValue(noOfChilds.getValue()+1);
        totalCost.setValue((noOfChilds.getValue()+noOfAdults.getValue())*price);
    }

    public void decrementChilds(View v){
        if (noOfChilds.getValue()>0) {
            noOfChilds.setValue(noOfChilds.getValue()-1);
            totalCost.setValue((noOfChilds.getValue()+noOfAdults.getValue())*price);

        }
    }

    public void postBookedPackages(){
        Call<ApiResponse<String>> call = Apiservice.getInstance().apiRequest.postBookedPackages( new BookedPackage(1,2,5,5));
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                }
                else {
                    //Toast.makeText(mcontext,"auth failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.d("tag", "articles total result:: " + t.getMessage());

            }
        });
    }


}
