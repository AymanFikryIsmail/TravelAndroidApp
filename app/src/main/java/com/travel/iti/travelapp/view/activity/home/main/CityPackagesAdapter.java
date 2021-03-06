package com.travel.iti.travelapp.view.activity.home.main;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;
import com.travel.iti.travelapp.view.activity._package.PackageActivity;
import java.util.ArrayList;
import java.util.List;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

/**
 * Created by ayman on 2019-05-18.
 */

public class CityPackagesAdapter extends RecyclerView.Adapter<CityPackagesAdapter.MyViewHolder> {

    private Context context;
    private List<CityPackage> cityPackageList = new ArrayList<>();

    public CityPackagesAdapter(Context context, List<CityPackage> operatorTitle) {
        this.context = context;
        this.cityPackageList = operatorTitle;
    }

    @Override
    public CityPackagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_package_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityPackagesAdapter.MyViewHolder holder, int position) {
        holder.bind(cityPackageList.get(position));
    }


    @Override
    public int getItemCount() {
        return cityPackageList.size();
    }


    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView cityNameId, cityDescId;
        public ImageView cityFavId , cityImageId;
        public CardView gridCardView;
        public LinearLayout dataLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            cityNameId=itemView.findViewById(R.id.cityNameId);
            cityDescId=itemView.findViewById(R.id.cityDescId);
            cityImageId=itemView.findViewById(R.id.cityImageId);
            cityFavId=itemView.findViewById(R.id.cityFavId);
            dataLayout=itemView.findViewById(R.id.dataLayoutId);
//            gridCardView=itemView.findViewById(R.id.gridCardView);

        }

        public void bind(final CityPackage cityPackage) {

            cityNameId.setText(cityPackage.getCityName());
            cityDescId.setText(cityPackage.getCityDesc());
            Picasso.with(context).load(BASE_URL+cityPackage.getCityImage())//
                    .fit().centerCrop()
                    .placeholder(R.drawable.mask)
                    .error(R.drawable.mask)
                    .into(cityImageId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PackageActivity.class);
                    intent.putExtra("cityPackage", cityPackage);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }


    public void updateList(List<CityPackage> newlist) {
        cityPackageList = newlist;
        this.notifyDataSetChanged();
    }


}