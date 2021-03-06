package com.travel.iti.travelapp.view.activity.recent_packages;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.home.main.MainView;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterBottomSheetFragment;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterFragmentInterface;
import com.travel.iti.travelapp.view.activity.recent_packages.sort.SortBottomSheetFragment;
import com.travel.iti.travelapp.view.activity.recent_packages.search.SearchActivity;
import com.travel.iti.travelapp.view.activity.recent_packages.sort.SortFragmentinterface;

import java.util.ArrayList;
import java.util.List;

public class RecentActivity extends AppCompatActivity implements FilterFragmentInterface, SortFragmentinterface  , MainView {

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
    FrameLayout progressView;
    public static int maxPrice = 0 ;
    public static int maxDays = 0 ;

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
        packagesViewModel.init(this);
        progressView= findViewById(R.id.progress_view);
        progressView.setVisibility(View.VISIBLE);
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
        searchEditText.setFocusable(false);
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

                for (PackagesPojo packagesPojo : packagesPojos)
                {
                    if (packagesPojo.getPrice() >= maxPrice ){
                        maxPrice = packagesPojo.getPrice();
                    }

                    if (packagesPojo.getDuration() >= maxDays){
                        maxDays = packagesPojo.getDuration() ;
                    }


                }

                System.out.println(maxPrice);
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

                for (PackagesPojo packagesPojo : packagesPojos)
                {
                    if (packagesPojo.getPrice() >= maxPrice ){
                        maxPrice = packagesPojo.getPrice();
                    }

                    if (packagesPojo.getDuration() >= maxDays){
                        maxDays = packagesPojo.getDuration() ;
                    }


                }

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

                for (PackagesPojo packagesPojo : packagesPojos)
                {
                    if (packagesPojo.getPrice() >= maxPrice ){
                        maxPrice = packagesPojo.getPrice();
                    }

                    if (packagesPojo.getDuration() >= maxDays){
                        maxDays = packagesPojo.getDuration() ;
                    }


                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null){

        }
        else if (requestCode == 2) {
            fromCity = data.getStringExtra("fromCity");
            toCity = data.getStringExtra("toCity");
            packagesAdapter.searchByCityFilter(fromCity,toCity);
            recyclerView.setAdapter(packagesAdapter);
            }

    }


    @Override
    public void passData(int price, int duration, int startOfRate , String first_date_check , String last_date_check) {
        packagesAdapter.filter(price, duration, startOfRate , first_date_check , last_date_check);
        recyclerView.setAdapter(packagesAdapter);
    }


    @Override
    public void passSortData(String priceRange, String sortType, int price) {

        if (sortType.equals("Rating")) {
            packagesAdapter.sortByRate(priceRange);
        } else if (sortType.equals("Date")) {
            packagesAdapter.sortByDate(priceRange);
        }else if (sortType.equals("Price")){
            packagesAdapter.sortByPrice(priceRange);
        }

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


}