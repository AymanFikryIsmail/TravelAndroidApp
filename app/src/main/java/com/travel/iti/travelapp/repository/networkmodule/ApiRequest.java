package com.travel.iti.travelapp.repository.networkmodule;

import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ayman on 2019-05-13.
 */

public interface  ApiRequest {


    @POST("users/login")
    Call<ApiResponse<User>> SignIn(@Body User body);

    @POST("users/signup")
    Call<ApiResponse<User>> signup(@Body User body);

    @GET("packages/city")
    Call<ApiResponse<List<CityPackage>>> getPackageCity();

}





