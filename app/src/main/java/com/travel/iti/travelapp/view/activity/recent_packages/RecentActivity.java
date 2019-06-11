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
import android.widget.EditText;
import android.widget.TextView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterBottomSheetFragment;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterFragmentInterface;
import com.travel.iti.travelapp.view.activity.recent_packages.sort.SortBottomSheetFragment;
import com.travel.iti.travelapp.view.activity.recent_packages.search.SearchActivity;
import com.travel.iti.travelapp.view.activity.recent_packages.search.SearchAdapter;
import com.travel.iti.travelapp.view.activity.recent_packages.sort.SortFragmentinterface;

import java.util.ArrayList;
import java.util.List;

public class RecentActivity extends AppCompatActivity implements FilterFragmentInterface, SortFragmentinterface {

    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    private TextView filterBtn;
    private Button sortBtn;
    private EditText searchEditText;
    public static final String TAG = "bottom_sheet";
    public static final String SORT_TAG = "sort_bottom_sheet";
    private String fromCity;
    private String toCity;

    private PrefManager prefManager;

    public RecentActivity() {
        packagesPojoList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        prefManager = new PrefManager(this);

        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList, packagesViewModel);
        packagesPojoList = null;

        String keyValue="recent";

        if (getIntent().getSerializableExtra("key")!=null)
        keyValue = getIntent().getStringExtra("key");
        if (keyValue.equals("recent")) {
            getRecentPackages();
        } else if (keyValue.equals("recommended")) {
            getRecommendedPackages();
        }
        else {
            getAllOffesrsPackages ();
        }

        filterBtn = findViewById(R.id.filter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterBottomSheetFragment filterFragment = new FilterBottomSheetFragment();
                filterFragment.show(getSupportFragmentManager(), TAG);
            }
        });

        sortBtn = findViewById(R.id.sort_btn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortBottomSheetFragment sortBottomSheetFragment = new SortBottomSheetFragment();
                sortBottomSheetFragment.show(getSupportFragmentManager(), SORT_TAG);
            }
        });
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentActivity.this, SearchActivity.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    void getRecentPackages() {
        packagesViewModel.getRecentPackages(prefManager.getUserId());
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList, packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

    void getRecommendedPackages() {
        packagesViewModel.getRecommendedPackages(prefManager.getUserId());
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList, packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

    void getAllOffesrsPackages(){
        packagesViewModel.getAllOffesrsPackages(prefManager.getUserId());
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList, packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            fromCity = data.getStringExtra("fromCity");
            toCity = data.getStringExtra("toCity");
            packagesAdapter.searchByCityFilter(fromCity,toCity);
            recyclerView.setAdapter(packagesAdapter);
            }

    }


    @Override
    public void passData(int price, int duration, int startOfRate) {
        packagesAdapter.filter(price, duration, startOfRate);
        recyclerView.setAdapter(packagesAdapter);
    }


    @Override
    public void passSortData(String priceRange, String sortType) {

        if (sortType.equals("Rating")) {
            packagesAdapter.sortByRate(priceRange);
        } else if (sortType.equals("Date")) {
            packagesAdapter.sortByDate(priceRange);
        }

    }
}