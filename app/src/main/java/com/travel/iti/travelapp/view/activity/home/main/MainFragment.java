package com.travel.iti.travelapp.view.activity.home.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.view.activity.home.home_search_bar.HomeSearchActivity;
import com.travel.iti.travelapp.view.activity.home.home_search_bar.HomeSearchResultActivity;
import com.travel.iti.travelapp.view.activity.recent_packages.RecentActivity;
import com.travel.iti.travelapp.view.activity.recent_packages.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MainView{

    public MainFragment() {
        // Required empty public constructor
    }

    List<CityPackage> cityPackageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CityPackagesAdapter adapter;
    private MainViewModel mainViewModel;
    private EditText searchEditText ;
    ImageView recentPackages ;
    ImageView recommendedPackages ;
    ImageView allOffersPackages ;
    FrameLayout progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init(this);

        // prefManager=new PrefManager(getContext());
        progressView= view.findViewById(R.id.progress_view);
        progressView.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerViewId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CityPackagesAdapter(getContext(), cityPackageList);
        cityPackageList = null;
        //getCities();
        getData();

        searchEditText = view.findViewById(R.id.search_bar);
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext() , HomeSearchActivity.class);
                startActivity(i);
            }
        });

        recentPackages = view.findViewById(R.id.recentPackages);
        recommendedPackages = view.findViewById(R.id.recommendedPackages);
        allOffersPackages = view.findViewById(R.id.allOffersPackages);

        recentPackages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RecentActivity.class);
                i.putExtra("key" , "recent");
                startActivity(i);

            }
        });

        recommendedPackages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RecentActivity.class);
                i.putExtra("key" , "recommended");
                startActivity(i);

            }
        });

        allOffersPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext() , RecentActivity.class);
                i.putExtra("key" , "allOffers");
                startActivity(i);
            }
        });

        return view;
    }

    void getData(){
        mainViewModel.getData();
        mainViewModel.cityPackageData.observe(getActivity(), new Observer<List<CityPackage>>() {
            @Override
            public void onChanged(@Nullable List<CityPackage> cityPackages) {
                cityPackageList=cityPackages;
                adapter.updateList(cityPackageList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showSuccess(String success) {
        progressView.setVisibility(View.GONE);
       // Toast.makeText(getContext(), success , Toast.LENGTH_LONG).show();
    }

    @Override
    public void shwoError(String error) {
        progressView.setVisibility(View.GONE);
        Toast.makeText(getContext(), error , Toast.LENGTH_LONG).show();

    }


}
