package com.travel.iti.travelapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Packages;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.repository.networkmodule.ApiResponse;
import com.travel.iti.travelapp.repository.networkmodule.Apiservice;
import com.travel.iti.travelapp.view.adapter.AutoSuggestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView fromCity;
    private AutoCompleteTextView toCity;

    List<Packages> mList;
    AutoSuggestAdapter adapter;
    private Packages package_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mList = retrievePeople();
        toCity = (AutoCompleteTextView) findViewById(R.id.to_auto_complete);

        adapter = new AutoSuggestAdapter(this, R.layout.activity_main, R.id.lbl_name, mList);
        toCity.setAdapter(adapter);
        toCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                package_detail = (Packages) adapterView.getItemAtPosition(pos);
            }
        });
    }


    private List<Packages> retrievePeople() {

//        Call<ApiResponse<Packages>> call = Apiservice.getInstance().apiRequest.get_to_cities(package_detail);
//        call.enqueue(new Callback<ApiResponse<Packages>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<Packages>> call, Response<ApiResponse<Packages>> response) {
//                if (response.body().status == "true"&&response.body().data!=null  ) {
//                    package_detail.setValue(response.body().data);
//                }
//                else {
//
//                }
//            }
//            @Override
//            public void onFailure(Call<ApiResponse<Packages>> call, Throwable t) {
//                Log.d("tag", "articles total result:: " + t.getMessage());
//
//            }
//        });

        List<Packages> list = new ArrayList<Packages>();



        return list;
    }
}