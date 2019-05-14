package com.travel.iti.travelapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.view.activity.SignUpActivity;

/**
 * Created by ayman on 2019-05-13.
 */

public class LoginViewModel extends ViewModel {

    private Context context;
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<String> UserName = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        if(view == view.findViewById(R.id.btnLogin)) {
            User loginUser = new User(EmailAddress.getValue(), Password.getValue(), UserName.getValue());

            userMutableLiveData.setValue(loginUser);
        }else{
            Intent intent= new Intent(context, SignUpActivity.class);
            context.startActivity(intent);
        }

    }
}
