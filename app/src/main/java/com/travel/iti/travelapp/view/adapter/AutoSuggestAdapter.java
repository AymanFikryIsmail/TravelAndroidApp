package com.travel.iti.travelapp.view.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.Packages;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestAdapter extends ArrayAdapter<Packages>  {
    Context context;
    int resource, textViewResourceId;
    List<Packages> items, tempItems, suggestions;

    public AutoSuggestAdapter(Context context, int resource, int textViewResourceId, List<Packages> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Packages>(items); // this makes the difference.
        suggestions = new ArrayList<Packages>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_search_result, parent, false);
        }
        Packages packages = items.get(position);
        if (packages != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(packages.getTravelTo());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Packages) resultValue).getTravelTo();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Packages packages : tempItems) {
                    if (packages.getTravelTo().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(packages);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Packages> filterList = (ArrayList<Packages>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Packages packages : filterList) {
                    add(packages);
                    notifyDataSetChanged();
                }
            }
        }
    };
}