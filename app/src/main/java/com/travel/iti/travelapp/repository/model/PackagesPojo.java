package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PackagesPojo implements Serializable {

    @SerializedName("pid")
    private int packageId;
    @SerializedName("p_name")
    private String packageName;
    private String travel_from;
    private String travel_to;
    private int price;
    private int discounted_price;
    private int avail_tickets;
    private int duration;
    private String date;
    private String description;
    @SerializedName("c_id")
    private int cityId;
    private String addingDate;
    @SerializedName("paths")
    private List<String> photoPaths;
    public PackagesPojo() {
    }

    public PackagesPojo(int packageId, String packageName, String travel_from, String travel_to, int price, int discounted_price, int avail_tickets, int duration, String date, String description, int cityId, String addingDate) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.travel_from = travel_from;
        this.travel_to = travel_to;
        this.price = price;
        this.discounted_price = discounted_price;
        this.avail_tickets = avail_tickets;
        this.duration = duration;
        this.date = date;
        this.description = description;
        this.cityId = cityId;
        this.addingDate = addingDate;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTravel_from() {
        return travel_from;
    }

    public void setTravel_from(String travel_from) {
        this.travel_from = travel_from;
    }

    public String getTravel_to() {
        return travel_to;
    }

    public void setTravel_to(String travel_to) {
        this.travel_to = travel_to;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(int discounted_price) {
        this.discounted_price = discounted_price;
    }

    public int getAvail_tickets() {
        return avail_tickets;
    }

    public void setAvail_tickets(int avail_tickets) {
        this.avail_tickets = avail_tickets;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(String addingDate) {
        this.addingDate = addingDate;
    }


    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }
}
