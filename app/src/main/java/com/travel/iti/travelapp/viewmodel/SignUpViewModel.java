package com.travel.iti.travelapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.travel.iti.travelapp.repository.model.User;

public class SignUpViewModel extends ViewModel {

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

        User signUpUser = new User(EmailAddress.getValue(), Password.getValue(),UserName.getValue());

        userMutableLiveData.setValue(signUpUser);

    }

}
