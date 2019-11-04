package com.travel.iti.travelapp.view.activity.home.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.travel.iti.travelapp.view.activity.recent_packages.RecentActivity;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        mainViewModel.init(this);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        getData();
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        });
//        // prefManager=new PrefManager(getContext());
//        progressView= view.findViewById(R.id.progress_view);
//        progressView.setVisibility(View.VISIBLE);
//        recyclerView = view.findViewById(R.id.recyclerViewId);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        adapter = new CityPackagesAdapter(getContext(), cityPackageList);
//        cityPackageList = null;
//        //getCities();
//        getData();
//
//        searchEditText = view.findViewById(R.id.search_bar);
//        searchEditText.clearFocus();
//        searchEditText.setFocusable(false);
//        searchEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getContext() , HomeSearchActivity.class);
//                startActivity(i);
//            }
//        });
//
//        recentPackages = view.findViewById(R.id.imageCompanies);
//        recommendedPackages = view.findViewById(R.id.imageTrips);
////        allOffersPackages = view.findViewById(R.id.imageCompanies);
//
//        recentPackages.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v)
//            {
//                Intent i = new Intent(getContext(), RecentActivity.class);
//                i.putExtra("key" , "recent");
//                startActivity(i);
//
//            }
//        });
//
//        recommendedPackages.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v)
//            {
//                Intent i = new Intent(getContext(), RecentActivity.class);
//                i.putExtra("key" , "recommended");
//                startActivity(i);
//
//            }
//        });
//
////        allOffersPackages.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i = new Intent(getContext() , RecentActivity.class);
////                i.putExtra("key" , "allOffers");
////                startActivity(i);
////            }
////        });

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
    public void showError(String error) {
        progressView.setVisibility(View.GONE);
        Toast.makeText(getContext(), error , Toast.LENGTH_LONG).show();

    }


}
