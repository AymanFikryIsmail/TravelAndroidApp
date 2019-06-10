package com.travel.iti.travelapp.view.activity.package_details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityPackageDetailsBinding;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.booking.BookingActivity;
import com.travel.iti.travelapp.view.activity.home.MainActivity;

import java.util.HashMap;

import static com.travel.iti.travelapp.repository.networkmodule.NetworkManager.BASE_URL;

public class PackageDetailsActivity extends AppCompatActivity  {
    SliderLayout sliderLayout;
    private PackagesPojo packagesPojo;
    private ActivityPackageDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_package_details);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        binding = DataBindingUtil.setContentView(PackageDetailsActivity.this, R.layout.activity_package_details);
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);
        binding.setGotoBooking(this);

        sliderLayout = findViewById(R.id.imageSlider);
//        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        setSliderViews();

    }
    public void gotoBooking(View view){
        Intent intent=new Intent(this , BookingActivity.class);
        intent.putExtra("packageDetails", packagesPojo);
        startActivity(intent);
    }
    private void setSliderViews() {

        for (int i = 0; i < packagesPojo.getPhotoPaths().size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.setImageUrl(BASE_URL+packagesPojo.getPhotoPaths().get(i));
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
//            sliderView.setDescription(packagesPojo.getDescription());
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }

    }
}