package com.travel.iti.travelapp.view.activity.home.fragments.booking;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;
import com.travel.iti.travelapp.view.activity.home.fragments.favourite.FavouriteAdapter;

import java.util.List;

public class BookingsFragment extends Fragment {

    private BookingsViewModel mViewModel;
    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private BookingAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;
    private CityPackage cityPackage;

    public static BookingsFragment newInstance() {
        return new BookingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bookings_fragment, container, false);
        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new BookingAdapter(getContext(), packagesPojoList);
        packagesPojoList = null;
        getData();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookingsViewModel.class);
        // TODO: Use the ViewModel
    }


    void getData(){
        packagesViewModel.getData("luxor");
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }

}
