package com.travel.iti.travelapp.view.activity.qrcard;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityQrcardBinding;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

public class QRCardActivity extends AppCompatActivity {

    private PackagesPojo packagesPojo;
    private ActivityQrcardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qrcard);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        binding = DataBindingUtil.setContentView(QRCardActivity.this, R.layout.activity_qrcard);
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);
    }
}
