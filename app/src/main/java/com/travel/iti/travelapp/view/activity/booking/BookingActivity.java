package com.travel.iti.travelapp.view.activity.booking;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityBookingBinding;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.qrcard.QRCardActivity;

public class BookingActivity extends AppCompatActivity {

    private PackagesPojo packagesPojo;
    private ActivityBookingBinding binding;

    private BookingViewModel bookingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        binding = DataBindingUtil.setContentView(BookingActivity.this, R.layout.activity_booking);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.init(packagesPojo.getPrice());
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);
        binding.setBookingViewModel(bookingViewModel);
        binding.setGotoBooking(this);

        binding.decrementAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void gotoBooking(View view){
        Intent intent=new Intent(this , QRCardActivity.class);
        intent.putExtra("packageDetails", packagesPojo);
        startActivity(intent);
    }}
