package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2019-05-29.
 */

public class BookedPackage {

    @SerializedName("package")
    int packageId;
    @SerializedName("user")
    int userId;
    @SerializedName("adults")
    int noOfAdults;
    @SerializedName("children")
    int noOfChildren;

    public BookedPackage(int packageId, int userId, int noOfAdults, int noOfChildren) {
        this.packageId = packageId;
        this.userId = userId;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
    }
}
