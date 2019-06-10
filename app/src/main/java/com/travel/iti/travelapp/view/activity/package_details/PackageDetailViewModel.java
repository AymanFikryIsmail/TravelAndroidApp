package com.travel.iti.travelapp.view.activity.package_details;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.RatePackagePojo;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-06-10.
 */

public class PackageDetailViewModel  extends ViewModel{




    public void postRatePackages(int packageId , int userId  ,int companyId , float rateValue){
        RatePackagePojo bookedPackage= new RatePackagePojo(packageId,userId , companyId ,rateValue);
        Call<ApiResponse<String>> call = Apiservice.getInstance().apiRequest.
                postRatePackages(bookedPackage);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.body().status == "true"&&response.body().data!=null  ) {
                    Log.d("tag", "articles total result:: " + response.body().getMessage());
                   // bookingView.bookPackage(bookedPackage);

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
