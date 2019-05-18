package com.travel.iti.travelapp.view.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.view.fragments.BookingsFragment;
import com.travel.iti.travelapp.view.fragments.FavoritesFragment;
import com.travel.iti.travelapp.view.fragments.MyProfileFragment;
import com.travel.iti.travelapp.view.fragments.SettingsFragment;
import com.travel.iti.travelapp.view.fragments.ShareFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();break;
                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.favorites_fragment,
                                new FavoritesFragment()).commit();
                    case R.id.booking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.booking_fragment,
                                new BookingsFragment()).commit();
                    case R.id.myprofile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myprofile_fragment,
                                new MyProfileFragment()).commit();
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.settings_fragment,
                                new SettingsFragment()).commit();

                    case R.id.share:
                        getSupportFragmentManager().beginTransaction().replace(R.id.share_fragment,
                                new ShareFragment()).commit();
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Log out",Toast.LENGTH_SHORT).show();break;

                    default:
                        return true;
                }

                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

        @Override
    public void onBackPressed() {
            if (dl.isDrawerOpen(GravityCompat.START)){
                dl.closeDrawer(GravityCompat.START);
            }else {
                super.onBackPressed();
                finishAffinity();
            }

    }
}
