package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayman on 2019-05-29.
 */

public class BookedPackage extends PackagesPojo {

    @SerializedName("package")
    int packageId;
    String userName;
    @SerializedName("user")
    int userId;

    @SerializedName("adults")
    int noOfAdults;
    @SerializedName("children")
    int noOfChildren;


    public BookedPackage(int packageId, int userId) {
        this.packageId = packageId;
        this.userId = userId;

    }

    public BookedPackage(int packageId, int userId, int noOfAdults, int noOfChildren , String userName) {
        this.packageId = packageId;
        this.userId = userId;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
        this.userName=userName;
    }


    public int getPackageId() {
        return packageId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }
}
