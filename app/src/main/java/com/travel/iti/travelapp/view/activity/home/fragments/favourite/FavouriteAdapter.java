package com.travel.iti.travelapp.view.activity.home.fragments.favourite;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.travel.iti.travelapp.view.activity.package_details.PackageDetailsActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayman on 2019-05-22.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private Context context;
    private List<PackagesPojo> packagesPojoList;

    public FavouriteAdapter() {
        packagesPojoList = new ArrayList<>();
    }

    public FavouriteAdapter(Context context, List<PackagesPojo> packagesPojoList) {
        this.context = context;
        this.packagesPojoList = packagesPojoList;
    }

    @NonNull
    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_row, parent, false);
        return new FavouriteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MyViewHolder holder, int position) {
        holder.bind(packagesPojoList.get(position));
    }

    @Override
    public int getItemCount() {
        return packagesPojoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView travelTo ,date ,duration ,price ,availableTickets ,details;
        public ImageView   packageFavBtn , maskImage;
        public LinearLayout packageDataLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            travelTo = itemView.findViewById(R.id.travel_to);
            price = itemView.findViewById(R.id.price);
            availableTickets = itemView.findViewById(R.id.available_tickets);
            details = itemView.findViewById(R.id.details);
            packageFavBtn = itemView.findViewById(R.id.package_fav_btn);
            maskImage = itemView.findViewById(R.id.mask);

            packageDataLayout = itemView.findViewById(R.id.package_data_layout);
        }

        public void bind(final PackagesPojo packagesPojo) {
            travelTo.setText(packagesPojo.getTravel_to());
            price.setText(packagesPojo.getPrice()+"");
            availableTickets.setText("only "+packagesPojo.getAvail_tickets()+" avaliable");

            Picasso.with(context).load("http://172.16.5.220:3000/"+packagesPojo.getPrice())
                    .fit().centerCrop()
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(maskImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PackageDetailsActivity.class);
                    intent.putExtra("packageDetails", packagesPojo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

    public void updateList(List<PackagesPojo> newlist) {
        packagesPojoList = newlist;
        this.notifyDataSetChanged();
    }
}
