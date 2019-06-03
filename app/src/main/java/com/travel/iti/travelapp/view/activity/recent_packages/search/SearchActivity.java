package com.travel.iti.travelapp.view.activity.recent_packages.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<CityPackage> cityPackageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private SearchViewModel searchViewModel;
    private EditText searchFrom ;
    private EditText searchTO ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        searchAdapter = new SearchAdapter(getApplicationContext(), cityPackageList , searchViewModel);
        cityPackageList = null;

        getSearchedCities ();

        searchFrom = findViewById(R.id.fromEditText);
        searchFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchAdapter.filter(charSequence.toString());
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchTO = findViewById(R.id.toEditText);
        searchTO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView.setAdapter(searchAdapter);
                searchAdapter.filter(charSequence.toString());
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    void getSearchedCities(){
        searchViewModel.getSearchedCities();
        searchViewModel.searchedCity.observe(this, new Observer<List<CityPackage>>() {
            @Override
            public void onChanged(@Nullable List<CityPackage> cityPackages) {
                cityPackageList  = cityPackages;
                searchAdapter.updateList(cityPackageList , cityPackages);
                recyclerView.setAdapter(searchAdapter);
            }
        });
    }
}
