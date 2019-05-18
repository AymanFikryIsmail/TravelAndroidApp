package com.travel.iti.travelapp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Packages;

import java.util.List;

//public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder> {
//
//    private List <Packages> packages ;
//    private Context context ;
//
//    public RecyclerViewAdapter(List<Packages> packages, Context context) {
//        this.packages = packages;
//        this.context = context;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cities_item , parent,false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//        holder.city.setText(packages.get(position).getTravelTo());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return packages.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView city ;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            city = itemView.findViewById(R.id.edit_text_city);
//
//        }
//    }
//}
