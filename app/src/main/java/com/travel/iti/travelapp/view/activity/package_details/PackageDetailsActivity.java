package com.travel.iti.travelapp.view.activity.package_details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityPackageDetailsBinding;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.utils.rateBar.RatingDialog;
import com.travel.iti.travelapp.view.activity.booking.BookingActivity;
import com.travel.iti.travelapp.view.activity.booking.BookingViewModel;
import com.travel.iti.travelapp.view.activity.home.MainActivity;
import com.travel.iti.travelapp.view.activity.package_desc.PackageDescActivity;

import java.util.HashMap;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

public class PackageDetailsActivity extends AppCompatActivity {
    SliderLayout sliderLayout;
    private PackagesPojo packagesPojo;
    private ActivityPackageDetailsBinding binding;
    Button rateBtn;
    public RatingBar ratingBar;
    RatingDialog mRatingDialog;

    private float rateValue;
    private PrefManager prefManager;
    PackageDetailViewModel packageDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_package_details);
        packagesPojo = (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        binding = DataBindingUtil.setContentView(PackageDetailsActivity.this, R.layout.activity_package_details);
        packageDetailViewModel = ViewModelProviders.of(this).get(PackageDetailViewModel.class);
        prefManager = new PrefManager(this);
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);
        binding.setGotoBooking(this);
        sliderLayout = findViewById(R.id.imageSlider);
//        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        setSliderViews();

        mRatingDialog = new RatingDialog(PackageDetailsActivity.this);
        mRatingDialog.setDefaultRating((int) packagesPojo.getRate());
        mRatingDialog.setEnable(true);
        mRatingDialog.setDefaultRating((int) packagesPojo.getUser_rate());

        mRatingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() {
            @Override
            public void onDismiss() {
                Log.v("RATELISTERNER", "onDismiss ");
            }

            @Override
            public void onSubmit(float rating) {
                Log.v("RATELISTERNER", "onSubmit " + rating);
                rateValue = rating;
                mRatingDialog.setDefaultRating((int) rating);
                ratePackage();
            }

            @Override
            public void onRatingChanged(float rating) {
                Log.v("RATELISTERNER", "onRatingChanged " + rating);
                mRatingDialog.setDefaultRating((int) rating);
            }
        });

    }

    public void rate(View view) {

        mRatingDialog.showDialog();

    }

    public void ratePackage() {
        packageDetailViewModel.postRatePackages(packagesPojo.getPackageId(), prefManager.getUserId()
                , packagesPojo.getCompanyId(), rateValue);

    }

    public void gotoBooking(View view) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("packageDetails", packagesPojo);
        startActivity(intent);
    }
    public void gotoDescription(View view) {
        Intent intent = new Intent(this, PackageDescActivity.class);
        intent.putExtra("packageDetails", packagesPojo);
        startActivity(intent);
    }
    private void setSliderViews() {

        for (int i = 0; i < packagesPojo.getPhotoPaths().size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.setImageUrl(BASE_URL + packagesPojo.getPhotoPaths().get(i));
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
//            sliderView.setDescription(packagesPojo.getDescription());
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }

    }
}