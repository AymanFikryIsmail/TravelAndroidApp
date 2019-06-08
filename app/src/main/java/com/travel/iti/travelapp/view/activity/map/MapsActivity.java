package com.travel.iti.travelapp.view.activity.map;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.utils.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  ,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener {
    //private GoogleMap mMap;
    private Button saveLocation;
    private ProgressDialog myProgress;

    private static final String MYTAG = "MYTAG";
    // Request Code to ask the user for permission to view their current location (***).
// Value 8bit (value <256)
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    //    LocationHelper locationHelper;
//    Location mLocation;
    double latitude;
    double longitude;
//Google ApiClient

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map loading .....");
        myProgress.setMessage("Please wait ....");
        myProgress.setCancelable(true);
        // Display Progress Bar.
        myProgress.show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        saveLocation = findViewById(R.id.submitBtnMapId);
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                String address = "";
                String zipCode = "";
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (addresses.size() > 0 && addresses != null) {
                        address = addresses.get(0).getAdminArea();
                    } else {
                        address = "address is not determined ";
                    }
                    data.putExtra("address", address);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Sét OnMapLoadedCallback Listener.
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
                askPermissionsAndShowMyLocation();
            }
        });
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//            // return;
//        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }

//        getCurrentLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng newLocation = marker.getPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng newLocationLatLng = marker.getPosition();
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocationLatLng, 15.0f));
                mMap.clear();
                //Adding a new marker to the current pressed position we are also making the draggable true
                mMap.addMarker(new MarkerOptions().position(newLocationLatLng).draggable(true).title("موقعي")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocationLatLng));
                latitude = newLocationLatLng.latitude;
                longitude = newLocationLatLng.longitude;
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                LatLng newLocation = marker.getPosition();
            }

        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                // Creating a marker
                mMap.clear();
                //Adding a new marker to the current pressed position we are also making the draggable true
                mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("My location")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                latitude = latLng.latitude;
                longitude = latLng.longitude;


            }
        });
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void askPermissionsAndShowMyLocation() {
        // With API> = 23, you have to ask the user for permission to view their location.
        boolean isGPSEnabled = false;
        // flag for network status
        boolean isNetworkEnabled = false;
        // flag for GPS status
        boolean canGetLocation = false;
        LocationManager locationManager;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (Build.VERSION.SDK_INT >= 23) {
            // flag for GPS status
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);

                setResult(Activity.RESULT_CANCELED, data);
                finish();
            } else {
//            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
//                    || accessFinePermission != PackageManager.PERMISSION_GRANTED)

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // The Permissions to ask user.
                    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION};
                    // Show a dialog asking the user to allow the above permissions.
                    ActivityCompat.requestPermissions(this, permissions,
                            REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                    return;
                }
                this.showMyLocation();
            }

        } else {
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);

                setResult(Activity.RESULT_CANCELED, data);
                finish();
            } else {
                this.showMyLocation();
            }


        }
        // Show current location on Map.
    }

    // When you have the request results.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (read/write).
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);

                    }

                    // Show current location on Map.
                    this.showMyLocation();
                }
                // Cancelled or denied.
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
    private void showMyLocation() {
        getCurrentLocation();
    }
    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)             // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        // Add Marker to Map
        MarkerOptions option = new MarkerOptions();
        option.title(" Current Location");
        option.snippet("....");
        option.position(latLng);
        option   .draggable(true) ;//Making the marker draggable

        Marker currentMarker = mMap.addMarker(option);
        currentMarker.showInfoWindow();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
    //Getting current location
    private void getCurrentLocation() {

        mMap.clear();
        GPSTracker gps;
        gps = new GPSTracker(getApplicationContext());
        // check if GPS enabled
        if(gps.canGetLocation()){
            latitude =gps.getLatitude();//31.25841263
            longitude =gps.getLongitude();//29.98027325
            //   Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            moveMap();
        }else{

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        //   moveMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
//        googleApiClient.disconnect();
        super.onStop();
    }


}
