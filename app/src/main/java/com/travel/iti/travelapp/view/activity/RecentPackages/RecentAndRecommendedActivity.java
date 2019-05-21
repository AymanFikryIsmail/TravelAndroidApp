package com.travel.iti.travelapp.view.activity.RecentPackages;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.adapter.PackagesAdapter;
import com.travel.iti.travelapp.viewmodel.PackagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecentAndRecommendedActivity extends AppCompatActivity {

    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;

    public RecentAndRecommendedActivity() {
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
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList);
        packagesPojoList = null;
        String keyValue ;
        keyValue = getIntent().getStringExtra("key");
        if (keyValue == "recent"){
            getRecentPackages();
        }
        else{
            getRecommendedPackages ();
        }


    }

    void getRecentPackages(){
        packagesViewModel.getRecentPackages();
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList);
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
                packagesAdapter.updateList(packagesPojoList);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

}
