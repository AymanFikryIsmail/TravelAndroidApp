package com.travel.iti.travelapp.view.activity.home.home_search_bar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;
import com.travel.iti.travelapp.view.activity.home.MainActivity;
import com.travel.iti.travelapp.view.activity.home.main.MainView;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterBottomSheetFragment;
import com.travel.iti.travelapp.view.activity.recent_packages.filter.FilterFragmentInterface;
import com.travel.iti.travelapp.view.activity.recent_packages.sort.SortBottomSheetFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchResultActivity extends AppCompatActivity implements FilterFragmentInterface , MainView {

    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    private TextView filterBtn;
    private Button sortBtn;
    public static final String TAG = "bottom_sheet";
    public static final String SORT_TAG = "sort_bottom_sheet";
    private FilterFragmentInterface filterFragmentInterface;
    private String fromCity;
    private String toCity;
    private PrefManager prefManager;

    public HomeSearchResultActivity() {
        packagesPojoList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search_result);

        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);
        packagesViewModel.init(this);
        prefManager=new PrefManager(this);
        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList, packagesViewModel);
        packagesPojoList = null;

        getHomeSearchedPackages ();

        String keyValue;
        keyValue = getIntent().getStringExtra("key");
        if (keyValue.equals("search")){
            Intent i = getIntent();
            fromCity = i.getStringExtra("fromCity");
            toCity = i.getStringExtra("toCity");

        }


        filterBtn = findViewById(R.id.filter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterBottomSheetFragment filterFragment = new FilterBottomSheetFragment();
                filterFragment.show(getSupportFragmentManager(), TAG);
            }
        });

        sortBtn = findViewById(R.id.sort);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortBottomSheetFragment sortBottomSheetFragment = new SortBottomSheetFragment();
                sortBottomSheetFragment.show(getSupportFragmentManager(), SORT_TAG);
            }
        });
    }


    void getHomeSearchedPackages (){

        packagesViewModel.getHomeSearchedPackages(prefManager.getUserId());
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList, packagesPojos);
                recyclerView.setAdapter(packagesAdapter);
                packagesAdapter.searchByCityFilter(fromCity,toCity);

            }
        });

    }

    @Override
    public void passData(int price, int duration, int rate , String first_date_check , String last_date_check) {

        packagesAdapter.filter(price, duration, rate , first_date_check , last_date_check);
        recyclerView.setAdapter(packagesAdapter);

    }
    @Override
    public void showSuccess(String success) {
        // Toast.makeText(getContext(), success , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeSearchResultActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
