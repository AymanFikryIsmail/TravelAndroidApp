package com.travel.iti.travelapp.view.activity.recent_packages;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterBottomSheetFragment;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterFragmentInterface;

import java.util.ArrayList;
import java.util.List;

public class RecentActivity extends AppCompatActivity implements FilterFragmentInterface {

    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    private Button filterBtn ;
    private Button sortBtn ;
    public static final String TAG = "bottom_sheet";
    private FilterFragmentInterface filterFragmentInterface ;


    public RecentActivity() {
        packagesPojoList = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);
        packagesViewModel.init(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList,packagesViewModel);
        packagesPojoList = null;

        String keyValue;
        keyValue = getIntent().getStringExtra("key");
        if (keyValue.equals("recent")) {
            getRecentPackages();
        } else {
            getRecommendedPackages();
        }

        filterBtn = findViewById(R.id.filter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterBottomSheetFragment filterFragment = new FilterBottomSheetFragment();
                filterFragment.show(getSupportFragmentManager(), TAG);
            }
        });

    }

    void getRecentPackages(){
        packagesViewModel.getRecentPackages();
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList, packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

    void getRecommendedPackages(){
        packagesViewModel.getRecommendedPackages();
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList , packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

    @Override
    public void passData(int price, int duration, int rate) {

        packagesAdapter.filter (price , duration , rate);
        recyclerView.setAdapter(packagesAdapter);

    }
}
