package com.travel.iti.travelapp.view.activity.recent_packages.search;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.CityPackage;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<CityPackage> cityPackageList = new ArrayList<>();
    private SearchViewModel searchViewModel;
    private List<CityPackage> originList;
    CustomItemClickListener listener;

    public SearchAdapter(Context context, List<CityPackage> cityPackages, SearchViewModel searchViewModel ,
                         CustomItemClickListener listener) {
        this.context = context;
        this.cityPackageList = cityPackages;
        this.searchViewModel = searchViewModel;
        this.originList = new ArrayList<>();
        this.listener = listener ;
    }


    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_items_row, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.bind(cityPackageList.get(position));

    }

    @Override
    public int getItemCount() {
        return cityPackageList.size();
    }


    ////////////////////////////////////////////////////////////////////

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView cityName;

        public MyViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.cityItem);

        }

        public void bind(final CityPackage cityPackage) {

            cityName.setText(cityPackage.getCityName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(cityPackage.getCityName());
                }
            });
        }

    }


    public void updateList(List<CityPackage> newlist, List<CityPackage> originNewList) {
        cityPackageList = newlist;
        this.originList.addAll(originNewList);
        this.notifyDataSetChanged();
    }

    public void filter(String searchWord) {

        searchWord = searchWord.toLowerCase();
        cityPackageList.clear();

        if (searchWord.isEmpty()) {
            cityPackageList.addAll(originList);
        } else {

            List<CityPackage> filteredList = new ArrayList<>();
            for (CityPackage cityPackage : originList) {

                if ((cityPackage.getCityName().toLowerCase().contains(searchWord))) {
                    filteredList.add(cityPackage);
                }
            }

            cityPackageList = filteredList;
            this.notifyDataSetChanged();
        }
    }

}

