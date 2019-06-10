package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2019-06-10.
 */

public class RatePackagePojo extends BookedPackage {


    @SerializedName("company")
    int companyId;
    @SerializedName("value")
    float rateValue;

    public RatePackagePojo(int packageId, int userId, int companyId ,float rateVal) {
        super(packageId, userId);
        this.companyId=companyId;
        this.rateValue=rateVal;
    }


    public int getCompanyId() {
        return companyId;
    }

    public float getRateValue() {
        return rateValue;
    }
}
