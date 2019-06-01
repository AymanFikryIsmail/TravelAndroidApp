package com.travel.iti.travelapp.view.activity.recent_packages.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;
import com.travel.iti.travelapp.view.activity._package.PackagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<PackagesPojo> packagesPojoList;
    private PackagesViewModel packagesViewModel;

    public SearchAdapter() {
        packagesPojoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public SearchAdapter(Context context, List<PackagesPojo> packagesPojoList, PackagesViewModel packagesViewModel) {
        this.context = context;
        this.packagesPojoList = packagesPojoList;
        this.packagesViewModel = packagesViewModel;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    }

