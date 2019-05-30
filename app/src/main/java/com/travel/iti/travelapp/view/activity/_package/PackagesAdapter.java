package com.travel.iti.travelapp.view.activity._package;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {

    private Context context;
    private List<PackagesPojo> packagesPojoList;
    private List<PackagesPojo> originList;

    private PackagesViewModel packagesViewModel;

    public PackagesAdapter() {
        packagesPojoList = new ArrayList<>();
    }


    public PackagesAdapter(Context context, List<PackagesPojo> packagesPojoList, PackagesViewModel packagesViewModel) {
        this.context = context;
        this.packagesPojoList = packagesPojoList;
        this.originList=  new ArrayList<>();
        this.packagesViewModel = packagesViewModel;
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

        public TextView travelTo, date, duration, price, availableTickets, details;
        public ImageView roomBtn, packageFavBtn, maskImage;
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
            String strCurrentDate = packagesPojo.getDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date newDate = null;
            try {
                newDate = format.parse(strCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date(newDate.getTime());
            String d = format.format(currentDate);
            date.setText(d);

            duration.setText(packagesPojo.getDuration() + "");
            price.setText(packagesPojo.getPrice() + "");
            availableTickets.setText(packagesPojo.getAvail_tickets() + "");

            Picasso.with(context).load("http://172.16.5.220:3000/" + packagesPojo.getPhotoPaths().get(0))
                    .fit().centerCrop()
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(maskImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PackageDetailsActivity.class);
                    intent.putExtra("packageDetails", packagesPojo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            packageFavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    packagesViewModel.setFavPackage(packagesPojo.getPackageId());
                }
            });

            packagesViewModel.isFavPressed.observeForever(new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean)
                        packageFavBtn.setImageResource(R.drawable.ic_user);
                    else
                        packageFavBtn.setImageResource(R.drawable.ic_favorite);
                }
            });
        }
    }

    public void updateList(List<PackagesPojo> newlist ,List<PackagesPojo> originNewList ) {
        packagesPojoList = newlist;
        this.originList.addAll(originNewList) ;
        this.notifyDataSetChanged();
    }

    public void filter(int price  , int duration , int rate ){

        packagesPojoList.clear();
        List<PackagesPojo> filteredList = new ArrayList<>();
        for (PackagesPojo packagesPojo : originList){

            if ((price <= packagesPojo.getPrice()) && (duration <= packagesPojo.getDuration())){
                filteredList.add (packagesPojo) ;
            }
        }

        packagesPojoList = filteredList ;
        this.notifyDataSetChanged();

    }

}
