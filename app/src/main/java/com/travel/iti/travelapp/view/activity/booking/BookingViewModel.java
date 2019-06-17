package com.travel.iti.travelapp.view.activity.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
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
    public MutableLiveData<Integer> noOfAvails;

    public MutableLiveData<Double> totalCost;
    public MutableLiveData<String> userName;

    public BookingViewModel() {
        noOfAdults = new MutableLiveData<>();
        noOfChilds = new MutableLiveData<>();
        noOfAvails = new MutableLiveData<>();
        totalCost = new MutableLiveData<>();
        userName = new MutableLiveData<>();

        noOfAdults.setValue(0);
        noOfChilds.setValue(0);

        totalCost.setValue(0.0);

    }

    BookingView bookingView;
    PackagesPojo packagesPojo;

    public void init(PackagesPojo packagesPojo, BookingView bookingView) {
        this.packagesPojo = packagesPojo;
        this.bookingView = bookingView;
        noOfAvails.setValue(packagesPojo.getAvail_tickets());
    }

    public void incrementAdults(View v) {
        if (noOfAvails.getValue() > 0) {
            noOfAvails.setValue(noOfAvails.getValue() - 1);
            noOfAdults.setValue(noOfAdults.getValue() + 1);
            totalCost.setValue((double) ((noOfChilds.getValue() + noOfAdults.getValue()) * packagesPojo.getPrice()));

        }
    }

    public void decrementAdults(View v) {
        if (noOfAdults.getValue() > 0) {
            noOfAvails.setValue(noOfAvails.getValue() + 1);
            noOfAdults.setValue(noOfAdults.getValue() - 1);
            totalCost.setValue((double) ((noOfChilds.getValue() + noOfAdults.getValue()) * packagesPojo.getPrice()));

        }
    }

    public void incrementChilds(View v) {
        if (noOfAvails.getValue() > 0) {
            noOfAvails.setValue(noOfAvails.getValue() - 1);
            noOfChilds.setValue(noOfChilds.getValue() + 1);
            totalCost.setValue((double) ((noOfChilds.getValue() + noOfAdults.getValue()) * packagesPojo.getPrice()));
        }
    }

    public void decrementChilds(View v) {
        if (noOfChilds.getValue() > 0) {
            noOfAvails.setValue(noOfAvails.getValue() + 1);
            noOfChilds.setValue(noOfChilds.getValue() - 1);
            totalCost.setValue((double) ((noOfChilds.getValue()* packagesPojo.getDiscounted_price())
                    + (noOfAdults.getValue()* packagesPojo.getPrice()) ));

        }
    }

    public void postBookedPackages(int packageId, int userId) {

        if(!userName.getValue().isEmpty()) {


            if (noOfChilds.getValue() != 0 || noOfAdults.getValue() != 0) {

                BookedPackage bookedPackage = new BookedPackage(packageId, userId, noOfAdults.getValue(),
                        noOfChilds.getValue(), userName.getValue(), totalCost.getValue());
                Call<ApiResponse<String>> call = Apiservice.getInstance().apiRequest.
                        postBookedPackages(bookedPackage);
                call.enqueue(new Callback<ApiResponse<String>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                        if (response.body().status == "true" && response.body().data != null) {
                            Log.d("tag", "articles total result:: " + response.body().getMessage());
                            bookingView.bookPackage(bookedPackage);
                            bookingView.showSuccess("");
                            Log.d("tag", "articles total result:: " + response.body().getMessage());
                        } else {
                            bookingView.shwoError("Error in connection");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                        Log.d("tag", "articles total result:: " + t.getMessage());
                        bookingView.shwoError("Error in connection");

                    }
                });

            } else {

                bookingView.shwoError(" Number of passengers can't be 0");

            }
        }else {
            bookingView.shwoError(" You must enter the user name");

        }
    }


}
