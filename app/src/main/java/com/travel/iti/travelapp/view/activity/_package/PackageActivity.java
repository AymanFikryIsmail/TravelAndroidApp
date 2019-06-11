package com.travel.iti.travelapp.view.activity._package;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

import java.util.ArrayList;
import java.util.List;

public class PackageActivity extends AppCompatActivity {

    ImageView sliderLayout;
    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    public PackageActivity() {
        packagesPojoList = new ArrayList<>();
    }

    PrefManager prefManager;
    private CityPackage cityPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        cityPackage= (CityPackage) getIntent().getSerializableExtra("cityPackage");

        prefManager= new PrefManager(this);
        sliderLayout = findViewById(R.id.packageImage);

        Picasso.with(this).load("http://172.16.5.220:3000/"+cityPackage.getCityImage())
                .placeholder(R.drawable.mask)
                .error(R.drawable.mask)
                .into(sliderLayout);
        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);

        recyclerView = findViewById(R.id.packages_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList, packagesViewModel);
        packagesPojoList = null;
        getData();
    }
    void getData(){
        packagesViewModel.getData(cityPackage.getCityName() , prefManager.getUserId()) ;
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList , packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }

        });
    }
}
