package com.travel.iti.travelapp.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.view.adapter.CityPackagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    List<CityPackage> cityPackageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CityPackagesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // prefManager=new PrefManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CityPackagesAdapter(getContext(), cityPackageList);
        cityPackageList = null;
        getCities();
        return view;
    }

    void getCities() {
        cityPackageList=new ArrayList<>();
        cityPackageList.add(new CityPackage("cairo", "cairo is beautiful" ,
                "https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" , true));
        cityPackageList.add(new CityPackage("alex", "cairo is beautiful" ,
                "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260" , true));
        cityPackageList.add(new CityPackage("sharm", "cairo is beautiful" , "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260" , true));
        cityPackageList.add(new CityPackage("hurgada", "cairo is beautiful" , "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260" , true));
        cityPackageList.add(new CityPackage("marse", "cairo is beautiful" , "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260" , true));
        cityPackageList.add(new CityPackage("fayoum", "cairo is beautiful" , "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260" , true));

        adapter.updateList(cityPackageList);
        recyclerView.setAdapter(adapter);
    }

}
