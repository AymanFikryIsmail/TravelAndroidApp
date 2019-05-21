package com.travel.iti.travelapp.repository.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2019-05-13.
 */

public class User {

    int id;
    String email;
    String password;
    @SerializedName("name")
    String username;
    @SerializedName("user_phone")
    String phone;
    @SerializedName("city")
    String city;

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public User(String email, String password, String username, String phone , String city) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone=phone;
        this.city=city;
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
}
