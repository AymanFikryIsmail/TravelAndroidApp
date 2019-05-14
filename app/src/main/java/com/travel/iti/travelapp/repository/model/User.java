package com.travel.iti.travelapp.repository.model;

/**
 * Created by ayman on 2019-05-13.
 */

public class User {

    String email;
    String password;
    String username;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
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
