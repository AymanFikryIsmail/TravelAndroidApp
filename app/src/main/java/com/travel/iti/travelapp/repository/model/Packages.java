package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

public class Packages {

    int id;
    String travelFrom ;
    @SerializedName("travelTo")
    String travelTo;
    int price ;
    int duration ;

    public int getId() {
        return id;
    }

    public String getTravelFrom() {
        return travelFrom;
    }

    public String getTravelTo() {
        return travelTo;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public Packages(String travelFrom, String travelTo, int price, int duration) {

        this.travelFrom = travelFrom;
        this.travelTo = travelTo;
        this.price = price;
        this.duration = duration;
    }

    public Packages(String travelTo) {
        this.travelTo = travelTo;
    }


}
