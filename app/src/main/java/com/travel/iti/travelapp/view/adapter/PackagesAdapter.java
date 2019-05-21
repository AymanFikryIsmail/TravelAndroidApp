package com.travel.iti.travelapp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

import java.util.ArrayList;
import java.util.List;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {

private Context context;
private List<PackagesPojo> packagesPojoList;

    public PackagesAdapter() {
        packagesPojoList = new ArrayList<>();
    }


    public PackagesAdapter(Context context, List<PackagesPojo> packagesPojoList) {
        this.context = context;
        this.packagesPojoList = packagesPojoList;
    }

    @NonNull
    @Override
    public PackagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_package_row, parent, false);
        return new PackagesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(packagesPojoList.get(position));
    }

    @Override
    public int getItemCount() {
        return packagesPojoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView travelTo ,date ,duration ,price ,availableTickets ,details;
        public ImageView roomBtn , packageFavBtn , maskImage;
        public LinearLayout packageDataLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            travelTo = itemView.findViewById(R.id.travel_to);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            price = itemView.findViewById(R.id.price);
            availableTickets = itemView.findViewById(R.id.available_tickets);
            details = itemView.findViewById(R.id.details);

//            roomBtn = itemView.findViewById(R.id.room_btn);
            packageFavBtn = itemView.findViewById(R.id.package_fav_btn);
            maskImage = itemView.findViewById(R.id.mask);

            packageDataLayout = itemView.findViewById(R.id.package_data_layout);
        }

        public void bind(final PackagesPojo packagesPojo) {
            travelTo.setText(packagesPojo.getTravel_to());
            date.setText(packagesPojo.getDate());
            duration.setText(packagesPojo.getDuration()+"");
            price.setText(packagesPojo.getPrice()+"");
            availableTickets.setText(packagesPojo.getAvail_tickets()+"");

            Picasso.with(context).load("http://172.16.5.220:3000/"+packagesPojo.getPrice())
                    .fit().centerCrop()
                    .placeholder(R.drawable.recommended)
                    .error(R.drawable.recent)
                    .into(maskImage);

        }
    }

    public void updateList(List<PackagesPojo> newlist) {
        packagesPojoList = newlist;
        this.notifyDataSetChanged();
    }


}
