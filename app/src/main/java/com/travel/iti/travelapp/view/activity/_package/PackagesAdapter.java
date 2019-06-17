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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.package_details.PackageDetailsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {

    private Context context;
    private List<PackagesPojo> packagesPojoList;
    private List<PackagesPojo> originList;
    private PackagesViewModel packagesViewModel;

    private PrefManager prefManager;
    public PackagesAdapter() {
        packagesPojoList = new ArrayList<>();
    }


    public PackagesAdapter(Context context, List<PackagesPojo> packagesPojoList, PackagesViewModel packagesViewModel) {
        this.context = context;
        prefManager=new PrefManager(context);
        this.packagesPojoList = packagesPojoList;
        this.originList = new ArrayList<>();
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
        public RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            travelTo = itemView.findViewById(R.id.travel_to);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            price = itemView.findViewById(R.id.price);
            availableTickets = itemView.findViewById(R.id.available_tickets);
            details = itemView.findViewById(R.id.details);
            ratingBar = itemView.findViewById(R.id.ratingBar);

//            roomBtn = itemView.findViewById(R.id.room_btn);
            packageFavBtn = itemView.findViewById(R.id.package_fav_btn);
            maskImage = itemView.findViewById(R.id.mask);

            packageDataLayout = itemView.findViewById(R.id.package_data_layout);
        }

        public void bind(final PackagesPojo packagesPojo) {
            travelTo.setText(packagesPojo.getPackageName());
            date.setText(packagesPojo.getDate());
            duration.setText(packagesPojo.getDuration() + " Days");
            price.setText(packagesPojo.getPrice() + " LE");
            availableTickets.setText("Only " + packagesPojo.getAvail_tickets() + " Person available");
            ratingBar.setRating(packagesPojo.getRate());
            Picasso.with(context).load(BASE_URL + packagesPojo.getPhotoPaths().get(0))
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

            if (packagesPojo.getFav_flag()==1)
                packageFavBtn.setImageResource(R.drawable.ic_favorite_fill);
            else
                packageFavBtn.setImageResource(R.drawable.ic_favorite);

            packageFavBtn.setOnClickListener((View v) -> {
                packagesViewModel.setFavPackage(packagesPojo.getPackageId(),  prefManager.getUserId(),(boolean isFav) -> {
                            if (isFav)
                                packageFavBtn.setImageResource(R.drawable.ic_favorite_fill);
                            else
                                packageFavBtn.setImageResource(R.drawable.ic_favorite);
                        }
                );
            });

        }
    }

    public void updateList(List<PackagesPojo> newlist, List<PackagesPojo> originNewList) {
        packagesPojoList = newlist;
        this.originList.addAll(originNewList);
        this.notifyDataSetChanged();
    }

    public void filter(int price, int duration, int startOfRate , String first_date_check , String last_date_check) {

        packagesPojoList.clear();
        List<PackagesPojo> filteredList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date first_date = null ;
        Date second_date = null ;
        try {
            first_date = format.parse(first_date_check);
            second_date = format.parse(last_date_check);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (PackagesPojo packagesPojo : originList) {
            Date package_date = null ;
            try {
                package_date = format.parse(packagesPojo.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ///&& (package_date.compareTo(first_date) >= 0) && (package_date.compareTo(second_date) <= 0)
            if ((packagesPojo.getPrice() <= price) && (packagesPojo.getDuration() <= duration) && (packagesPojo.getRate() >= startOfRate) && (package_date.compareTo(first_date) >= 0) && (package_date.compareTo(second_date) <= 0)) {
                filteredList.add(packagesPojo);
            }

        }

        packagesPojoList = filteredList;
        this.notifyDataSetChanged();

    }

    public void searchByCityFilter(String fromCity , String toCity){

        packagesPojoList.clear();
        List<PackagesPojo> filteredList = new ArrayList<>();

        if (fromCity.equals("") && toCity.equals("") ){
           filteredList = originList ;
        }

        else {
            for (PackagesPojo packagesPojo : originList){

            if ((packagesPojo.getTravel_from().equals(fromCity)) && (packagesPojo.getTravel_to().equals(toCity))){
                filteredList.add (packagesPojo);
            }

            else if (packagesPojo.getTravel_from().equals(fromCity) && (toCity.equals(""))){
                filteredList.add(packagesPojo);
            }

            else if (fromCity.equals("") && packagesPojo.getTravel_to().equals(toCity)){
                filteredList.add (packagesPojo);
            }
            else {

            }
        }
        }
        packagesPojoList = filteredList ;
        this.notifyDataSetChanged();


    }


    public void sortByDate(String sortType){
        Comparator<PackagesPojo> comparator = new Comparator<PackagesPojo>() {
            @Override
            public int compare(PackagesPojo o1, PackagesPojo o2) {
                if (sortType.equals("asc")){
                    return ( String.valueOf(o1.getDate())).compareToIgnoreCase(String.valueOf(o2.getDate()));
                }else {
                    return ( String.valueOf(o2.getDate())).compareToIgnoreCase(String.valueOf(o1.getDate()));
                }
            }
        };
        Collections.sort(packagesPojoList, comparator);
        notifyDataSetChanged();
    }

    public void sortByRate(String sortType) {
        Comparator<PackagesPojo> comparator = new Comparator<PackagesPojo>() {

            @Override
            public int compare(PackagesPojo o1, PackagesPojo o2) {
                if (sortType.equals("asc")){
                    return ( String.valueOf(o1.getRate())).compareToIgnoreCase(String.valueOf(o2.getRate()));

                }else {
                    return ( String.valueOf(o2.getRate())).compareToIgnoreCase(String.valueOf(o1.getRate()));

                }
            }
        };
        Collections.sort(packagesPojoList, comparator);
        notifyDataSetChanged();
    }

}
