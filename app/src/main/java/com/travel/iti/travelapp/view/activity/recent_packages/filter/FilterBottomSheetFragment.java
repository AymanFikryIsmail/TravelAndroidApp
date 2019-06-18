package com.travel.iti.travelapp.view.activity.recent_packages.filter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.travel.iti.travelapp.view.activity._package.PackagesAdapter;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.view.activity.recent_packages.RecentActivity;
import com.travel.iti.travelapp.view.activity.splash_and_welcomScreens.SplashActivity;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private PackagesAdapter packagesAdapter;
    SeekBar priceSeekBar;
    SeekBar daysSeekBar;
    TextView priceTextiew;
    TextView daysTextView;
    int priceProgress = 0;
    int daysProgress = 0;
    TextView dateTextView;
    String first_date_check = "";
    String last_date_check = "" ;
    Button rateThree;
    Button rateFour;
    Button rateFive;
    Button rateAll;
    int startOfRate;
    Button btnApply;
    private FilterFragmentInterface filterFragmentInterface;
    GradientDrawable btnShape3, btnShape4, btnShape5, btnShapeAll;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_filter_bottom_sheet, null);
        dialog.setContentView(view);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

        filterFragmentInterface = (FilterFragmentInterface) getActivity();

        priceSeekBar = view.findViewById(R.id.priceRangeSeekBar);
        priceSeekBar.setMax(RecentActivity.maxPrice);

        priceTextiew = view.findViewById(R.id.priceValue);
        priceTextiew.setText(priceSeekBar.getProgress() + "/" + priceSeekBar.getMax());

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                priceProgress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
                priceTextiew.setText(priceProgress + "/" + seekBar.getMax());
            }
        });

        daysSeekBar = view.findViewById(R.id.daysRangeSeekBar);
        daysSeekBar.setMax(RecentActivity.maxDays);

        daysTextView = view.findViewById(R.id.daysNumber);
        daysTextView.setText(daysSeekBar.getProgress() + "/" + daysSeekBar.getMax());

        daysSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                daysProgress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
                daysTextView.setText(daysProgress + "/" + seekBar.getMax());
            }
        });

        dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(),RangeDatePickerActivity.class);
                startActivityForResult(i , 2);

            }
        });

        rateThree = view.findViewById(R.id.rateThree);
        rateFour = view.findViewById(R.id.rateFour);
        rateFive = view.findViewById(R.id.rateFive);
        rateAll = view.findViewById(R.id.rateAll);

        rateThree.setOnClickListener(this);
        rateFour.setOnClickListener(this);
        rateFive.setOnClickListener(this);
        rateAll.setOnClickListener(this);

        btnApply = view.findViewById(R.id.filterBtn);
        btnApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (priceProgress == 0 ){
                    priceProgress = RecentActivity.maxPrice ;
                }
                if (daysProgress == 0 ){
                    daysProgress = RecentActivity.maxDays ;
                }
                if (filterFragmentInterface != null) {
                    filterFragmentInterface.passData(priceProgress, daysProgress, startOfRate ,first_date_check , last_date_check);
                }

                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        btnShape3 = (GradientDrawable) rateThree.getBackground().getCurrent();
        btnShape3.setColor(getResources().getColor(R.color.white));
        btnShape4 = (GradientDrawable) rateFour.getBackground().getCurrent();
        btnShape4.setColor(getResources().getColor(R.color.white));
        btnShape5 = (GradientDrawable) rateFive.getBackground().getCurrent();
        btnShape5.setColor(getResources().getColor(R.color.white));
        btnShapeAll = (GradientDrawable) rateAll.getBackground().getCurrent();
        btnShapeAll.setColor(getResources().getColor(R.color.white));
        switch (view.getId()) {

            case R.id.rateThree:
                startOfRate = 3;
                btnShape3.setColor(getResources().getColor(R.color.darkYellow));
                btnShape4.setColor(getResources().getColor(R.color.white));
                btnShape5.setColor(getResources().getColor(R.color.white));
                btnShapeAll.setColor(getResources().getColor(R.color.white));
                break;

            case R.id.rateFour:
                startOfRate = 4;
                btnShape4.setColor(getResources().getColor(R.color.darkYellow));
                btnShape3.setColor(getResources().getColor(R.color.white));
                btnShape5.setColor(getResources().getColor(R.color.white));
                btnShapeAll.setColor(getResources().getColor(R.color.white));
                break;
            case R.id.rateFive:
                startOfRate = 5;
                btnShape5.setColor(getResources().getColor(R.color.darkYellow));
                btnShape3.setColor(getResources().getColor(R.color.white));
                btnShape4.setColor(getResources().getColor(R.color.white));
                btnShapeAll.setColor(getResources().getColor(R.color.white));
                break;
            case R.id.rateAll:
                startOfRate = 1;
                btnShapeAll.setColor(getResources().getColor(R.color.darkYellow));
                btnShape3.setColor(getResources().getColor(R.color.white));
                btnShape4.setColor(getResources().getColor(R.color.white));
                btnShape5.setColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            String first_date = data.getStringExtra("firstDate");
            String last_date = data.getStringExtra("secondDate");
            first_date_check = data.getStringExtra("first_date_check");
            last_date_check = data.getStringExtra("last_date_check");

            dateTextView.setText(first_date + "  -  " + last_date);

//            packagesAdapter.searchByCityFilter(fromCity,toCity);
//            recyclerView.setAdapter(packagesAdapter);
        }

    }

}

