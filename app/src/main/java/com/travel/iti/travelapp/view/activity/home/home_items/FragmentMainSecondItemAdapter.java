package com.travel.iti.travelapp.view.activity.home.home_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Company;

import java.util.ArrayList;

public class FragmentMainSecondItemAdapter extends RecyclerView.Adapter<FragmentMainSecondItemAdapter.MyViewHolder> {

    ArrayList<Company> data = new ArrayList<>();

    public FragmentMainSecondItemAdapter(ArrayList<Company> data) {
        this.data = data;

    }

    @Override
    public FragmentMainSecondItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.companies_homelist_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FragmentMainSecondItemAdapter.MyViewHolder holder, int position) {

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