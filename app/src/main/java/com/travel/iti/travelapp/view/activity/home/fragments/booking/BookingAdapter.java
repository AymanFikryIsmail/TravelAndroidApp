package com.travel.iti.travelapp.view.activity.home.fragments.booking;

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.package_details.PackageDetailsActivity;
import com.travel.iti.travelapp.view.activity.qrcard.QRCardActivity;

import java.util.ArrayList;
import java.util.List;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

/**
 * Created by ayman on 2019-05-22.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    private Context context;
    private List<BookedPackage> packagesPojoList;

    public BookingAdapter() {
        packagesPojoList = new ArrayList<>();
    }


    public BookingAdapter(Context context, List<BookedPackage> packagesPojoList) {
        this.context = context;
        this.packagesPojoList = packagesPojoList;
    }

    @NonNull
    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_row, parent, false);
        return new BookingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.MyViewHolder holder, int position) {
        holder.bind(packagesPojoList.get(position));
    }

    @Override
    public int getItemCount() {
        return packagesPojoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView travelTo ,date ,departure , arrival;
        public ImageView  maskImage;
        public RatingBar ratingBar;
        public LinearLayout packageDataLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            travelTo = itemView.findViewById(R.id.travel_to);
            departure = itemView.findViewById(R.id.departureId);
            arrival = itemView.findViewById(R.id.arrivalId);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            date = itemView.findViewById(R.id.dateId);
            maskImage = itemView.findViewById(R.id.mask);
            packageDataLayout = itemView.findViewById(R.id.package_data_layout);
        }

        public void bind(final BookedPackage packagesPojo) {
            travelTo.setText(packagesPojo.getPackageName());
            departure.setText(packagesPojo.getTravel_from());
            arrival.setText(packagesPojo.getTravel_to());
            ratingBar.setRating(packagesPojo.getRate());
            date.setText(packagesPojo.getDate());

            Picasso.with(context).load(BASE_URL+packagesPojo.getPhotoPaths().get(0))
                    .fit().centerCrop()
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(maskImage);

            itemView.setOnClickListener((View v)-> {
                    Intent intent=new Intent(context, QRCardActivity.class);
                    intent.putExtra("packageDetails", packagesPojo);
                    double totalCost= (packagesPojo.getTickets()*packagesPojo.getNoOfAdults())+(packagesPojo.getDiscounted_tickets()*packagesPojo.getNoOfChildren());
                    BookedPackage bookedPackage = new BookedPackage(packagesPojo.getPackageId(), packagesPojo.getUserId(),
                            packagesPojo.getTickets(), packagesPojo.getDiscounted_tickets() ,
                    packagesPojo.getName(), totalCost);
                    intent.putExtra("bookedPackage", bookedPackage);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
            });
        }
    }

    public void updateList(List<BookedPackage> newlist) {
        packagesPojoList = newlist;
        this.notifyDataSetChanged();
    }


}
