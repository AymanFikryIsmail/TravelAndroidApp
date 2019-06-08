package com.travel.iti.travelapp.view.activity.package_desc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

public class PackageDescActivity extends AppCompatActivity {

    TextView texttransform  ,  textDesc ;
    private PackagesPojo packagesPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_desc);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");

         texttransform= findViewById(R.id.texttransformId);
         textDesc= findViewById(R.id.textDescId);




    }
}
