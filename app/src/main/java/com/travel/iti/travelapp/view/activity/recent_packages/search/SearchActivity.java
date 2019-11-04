package com.travel.iti.travelapp.view.activity.recent_packages.search;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
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
    private String fromCity ;
    private String toCity ;
    Button searchBtn ;
    private PrefManager prefManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

//        searchAdapter.setO
        searchAdapter = new SearchAdapter(getApplicationContext(), cityPackageList, searchViewModel, new CustomItemClickListener() {
            @Override
            public void onItemClick(String cityName) {
                boolean to = searchTO.hasFocus();
                boolean from= searchFrom.hasFocus();
                if (from){
                  searchFrom.setText(cityName);
                }else{
                    searchTO.setText(cityName);
                }
            }
        });
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
                searchAdapter.filter(charSequence.toString());
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });


        fromCity = searchFrom.getText().toString() ;
        toCity = searchTO.getText().toString();

        searchBtn = findViewById(R.id.searchResultBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fromCity = searchFrom.getText().toString().toLowerCase() ;
                toCity = searchTO.getText().toString().toLowerCase();

                Intent intent = new Intent();
                intent.putExtra("fromCity",fromCity);
                intent.putExtra("toCity",toCity);
                setResult(2, intent);
                finish();

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
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(searchAdapter);
            }
        });
    }


}
