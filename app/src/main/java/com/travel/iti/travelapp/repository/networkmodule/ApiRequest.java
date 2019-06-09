package com.travel.iti.travelapp.repository.networkmodule;

import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.repository.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("packages/city/packages")
    Call<ApiResponse<List<PackagesPojo>>> getPackage(@Query("city") String city);

    @GET("packages/recent")
    Call<ApiResponse<List<PackagesPojo>>> getRecentPackages();

    @GET("packages/recommended")
    Call<ApiResponse<List<PackagesPojo>>> getRecommendedPackages();

    @GET("packages/favorite")
    Call<ApiResponse<List<PackagesPojo>>> getFavouritePackages(@Query("user_id") int user_id);

    @GET("packages/favorite/update")
    Call<ApiResponse<Boolean>> postFavouritePackages(
            @Query("user_id") int user_id,
            @Query("package_id") int package_id);


    @POST("packages/booking")
    Call<ApiResponse<String>> postBookedPackages(
            @Body BookedPackage bookedPackage);

    @GET("packages/search/all")
    Call<ApiResponse<List<CityPackage>>> getSearchedCity();



}





