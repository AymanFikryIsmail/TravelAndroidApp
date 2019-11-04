package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2019-05-13.
 */

public class User {

    int id;
    String email;
    @SerializedName("passwd")
    String password;
    @SerializedName("name")
    String username;
    @SerializedName("phone")
    String phone;
    @SerializedName("city")
    String city;
    @SerializedName("city_id")
    int city_id;
    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public User(String username,String password,  String phone , int cityId) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone=phone;
        this.city_id=cityId;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getCity_id() {
        return city_id;
    }
}
