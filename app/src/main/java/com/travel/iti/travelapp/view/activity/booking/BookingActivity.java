package com.travel.iti.travelapp.view.activity.booking;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityBookingBinding;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.activity.home.MainActivity;
import com.travel.iti.travelapp.view.activity.home.main.MainView;
import com.travel.iti.travelapp.view.activity.payment.VisaPaymentActivity;
import com.travel.iti.travelapp.view.activity.qrcard.QRCardActivity;

public class BookingActivity extends AppCompatActivity implements BookingView {

    private PackagesPojo packagesPojo;
    private ActivityBookingBinding binding;

    private BookingViewModel bookingViewModel;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking);
        prefManager=new PrefManager(this);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        binding = DataBindingUtil.setContentView(BookingActivity.this, R.layout.activity_booking);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.init(packagesPojo , this);
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);
        binding.setBookingViewModel(bookingViewModel);
        binding.setGotoBooking(this);


    }
    public void gotoBooking(View view){
        bookingViewModel.postBookedPackages(packagesPojo.getPackageId() ,prefManager.getUserId() );
        //BookedPackage bookedPackage=new BookedPackage();

    }

    @Override
    public void bookPackage(BookedPackage bookedPackage) {
        Intent intent=new Intent(this , VisaPaymentActivity.class);
        intent.putExtra("packageDetails", packagesPojo);
        intent.putExtra("bookedPackage", bookedPackage);

        startActivity(intent);
    }


    @Override
    public void showSuccess(String success) {
       // progressView.setVisibility(View.GONE);
        // Toast.makeText(getContext(), success , Toast.LENGTH_LONG).show();
    }

    @Override
    public void shwoError(String error) {
        //progressView.setVisibility(View.GONE);
        Toast.makeText(this, error , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
