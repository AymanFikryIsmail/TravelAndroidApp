package com.travel.iti.travelapp.view.activity.home.fragments.favourite;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import java.util.ArrayList;
import java.util.List;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

/**
 * Created by ayman on 2019-05-22.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private Context context;
    private List<PackagesPojo> packagesPojoList;
    private FavoritesViewModel mViewModel;

    private PrefManager prefManager;
    public FavouriteAdapter() {
        packagesPojoList = new ArrayList<>();
    }

    public FavouriteAdapter(Context context, List<PackagesPojo> packagesPojoLis,FavoritesViewModel mViewModel) {
        this.context = context;
        prefManager=new PrefManager(context);
        this.packagesPojoList = packagesPojoList;
        this.mViewModel = mViewModel;

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
            price.setText(packagesPojo.getPrice()+"LE");
            availableTickets.setText("only "+packagesPojo.getAvail_tickets()+" avaliable");
            details.setText("include accomodation");
            Picasso.with(context).load(BASE_URL+packagesPojo.getPhotoPaths().get(0))
                    .fit().centerCrop()
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(maskImage);
            packageFavBtn.setOnClickListener((View v) -> {
                mViewModel.setFavPackage(packagesPojo.getPackageId(), prefManager.getUserId(),(boolean isFav) -> {
                                packageFavBtn.setImageResource(R.drawable.ic_favorite_fill);
                            packagesPojoList.remove(packagesPojo);
                            notifyDataSetChanged();
                        }
                );
            });


        }
    }

    public void updateList(List<PackagesPojo> newlist) {
        packagesPojoList = newlist;
        this.notifyDataSetChanged();
    }
}
