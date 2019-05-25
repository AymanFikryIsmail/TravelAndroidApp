package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayman on 2019-05-18.
 */

public class CityPackage implements Serializable{
    @SerializedName("travel_to")
    private String cityName;
    @SerializedName("p_name")
    private String cityDesc;
    @SerializedName("photo_path")
    private String cityImage;
    private boolean cityFav;

    public CityPackage() {
    }

    public CityPackage(String cityName, String cityDesc, String cityImage, boolean cityFav) {
        this.cityName = cityName;
        this.cityDesc = cityDesc;
        this.cityImage = cityImage;
        this.cityFav = cityFav;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }

    public String getCityImage() {
        return cityImage;
    }

    public void setCityImage(String cityImage) {
        this.cityImage = cityImage;
    }

    public boolean isCityFav() {
        return cityFav;
    }

    public void setCityFav(boolean cityFav) {
        this.cityFav = cityFav;
    }
}
