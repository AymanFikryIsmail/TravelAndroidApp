package com.travel.iti.travelapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Packages;
import com.travel.iti.travelapp.repository.networkmodule.ApiRequest;
import com.travel.iti.travelapp.repository.networkmodule.NetworkManager;
//import com.travel.iti.travelapp.view.adapter.RecyclerViewAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
        @Override
    public void onBackPressed() {

        super.onBackPressed();

        finishAffinity();
    }
}
