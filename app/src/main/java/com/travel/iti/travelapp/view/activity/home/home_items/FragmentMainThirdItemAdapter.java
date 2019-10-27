package com.travel.iti.travelapp.view.activity.home.home_items;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Company;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

import java.util.ArrayList;

public class FragmentMainThirdItemAdapter extends RecyclerView.Adapter<FragmentMainThirdItemAdapter.MyViewHolder> {

    ArrayList<PackagesPojo> data = new ArrayList<>();

    public FragmentMainThirdItemAdapter(ArrayList<PackagesPojo> data) {
        this.data = data;

    }

    @Override
    public FragmentMainThirdItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_homelist_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FragmentMainThirdItemAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}