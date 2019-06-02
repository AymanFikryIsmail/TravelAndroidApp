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
        priceSeekBar.setMax(3000);

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
        daysSeekBar.setMax(5);
        daysSeekBar.incrementProgressBy(1);

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

                if (filterFragmentInterface != null) {
                    filterFragmentInterface.passData(priceProgress, daysProgress, startOfRate);
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
                Toast.makeText(getActivity(), "rate is 3", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rateFour:
                startOfRate = 4;
                btnShape4.setColor(getResources().getColor(R.color.darkYellow));
                Toast.makeText(getActivity(), "rate is 4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rateFive:
                startOfRate = 5;
                btnShape5.setColor(getResources().getColor(R.color.darkYellow));
                Toast.makeText(getActivity(), "rate is 5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rateAll:
                startOfRate = 1;
                btnShapeAll.setColor(getResources().getColor(R.color.darkYellow));
                Toast.makeText(getActivity(), "rate is all", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

