package com.travel.iti.travelapp.view.activity.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

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

}
