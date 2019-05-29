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
import android.widget.ImageView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.view.activity.recent_packages.RecentActivity;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

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
        mainViewModel.init(getContext());

        // prefManager=new PrefManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CityPackagesAdapter(getContext(), cityPackageList);
        cityPackageList = null;
        //getCities();
        getData();

        recentPackages = view.findViewById(R.id.recentPackages);
        recommendedPackages = view.findViewById(R.id.recommendedPackages);
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

}
