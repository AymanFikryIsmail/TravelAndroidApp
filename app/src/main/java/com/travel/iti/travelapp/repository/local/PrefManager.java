package com.travel.iti.travelapp.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.travel.iti.travelapp.repository.model.User;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "firstTimeEnters";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";



    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public void setUserId(int uid){
        editor.putInt("uid", uid);
        editor.commit();
    }

    public int getUserId(){
        return  pref.getInt("uid", 0);
    }

    public void setUserData(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
    }

    public User getUserData(){
        Gson gson = new Gson();
        String json = pref.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

}
