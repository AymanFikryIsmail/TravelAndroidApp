package com.travel.iti.travelapp.view.activity._package;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
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
import com.travel.iti.travelapp.view.activity.home.main.MainView;

import java.util.ArrayList;
import java.util.List;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

public class PackageActivity extends AppCompatActivity implements MainView {

    ImageView   packageImage;
    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    public PackageActivity() {
        packagesPojoList = new ArrayList<>();
    }
    FrameLayout progressView;

    PrefManager prefManager;
    private CityPackage cityPackage;
    private  TextView package_city_name;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        cityPackage= (CityPackage) getIntent().getSerializableExtra("cityPackage");

        prefManager= new PrefManager(this);

        package_city_name= findViewById(R.id.package_city_name);
        package_city_name.setText(cityPackage.getCityName());
        packageImage= findViewById(R.id.packageImage);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);
        packagesViewModel.init(this);
        progressView= findViewById(R.id.progress_view);
        progressView.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.packages_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList, packagesViewModel);
        packagesPojoList = null;

            Picasso.with(this).load(BASE_URL+cityPackage.getCityImage())
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(packageImage);
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
    @Override
    public void showSuccess(String success) {
        progressView.setVisibility(View.GONE);
        // Toast.makeText(getContext(), success , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        progressView.setVisibility(View.GONE);
        Toast.makeText(this, error , Toast.LENGTH_LONG).show();

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cityPackage" ,cityPackage);

    }

}
