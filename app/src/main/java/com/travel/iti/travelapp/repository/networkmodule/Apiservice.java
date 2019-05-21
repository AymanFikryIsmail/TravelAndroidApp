package com.travel.iti.travelapp.repository.networkmodule;

import android.content.Context;

import com.travel.iti.travelapp.repository.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayman on 2019-05-13.
 */

public class Apiservice {
    public static Apiservice newInstance=null;
    public ApiRequest apiRequest =NetworkManager.getInstance().createService("");
    private Apiservice(){}

    public static Apiservice getInstance(){
        if (newInstance == null) {
            synchronized (Apiservice.class) {
                if (newInstance == null) {
                    newInstance = new Apiservice();
                }
            }
        }
        return newInstance;
    }
}
