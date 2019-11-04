package com.travel.iti.travelapp.view.activity.home.fragments.favourite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.home.main.MainView;

import java.util.List;

public class FavoritesFragment extends Fragment implements MainView {

    private FavoritesViewModel mViewModel;
    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private FavouriteAdapter packagesAdapter;
    private LinearLayout emptyLayout;
    FrameLayout progressView;

    private PrefManager prefManager;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mViewModel.init(this);
        prefManager = new PrefManager(getContext());
        progressView= view.findViewById(R.id.progress_view);
        progressView.setVisibility(View.VISIBLE);
        emptyLayout = view.findViewById(R.id.emptyLayoutId);
        recyclerView = view.findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new FavouriteAdapter(getContext(), packagesPojoList, mViewModel);
        packagesPojoList = null;
        getFavouritePackages();
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
    }

    void getFavouritePackages() {
        mViewModel.getFavouritePackages(prefManager.getUserId());
        mViewModel.pckageList.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                if (packagesPojos.size()==0){
                    emptyLayout.setVisibility(View.VISIBLE);
                }else {
                    emptyLayout.setVisibility(View.GONE);
                }
                packagesAdapter.updateList(packagesPojoList);
                recyclerView.setAdapter(packagesAdapter);
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
