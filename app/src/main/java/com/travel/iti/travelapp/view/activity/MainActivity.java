package com.travel.iti.travelapp.view.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.travel.iti.travelapp.R;

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
                        Toast.makeText(MainActivity.this, "favorites",Toast.LENGTH_SHORT).show();break;
                    case R.id.booking:
                        Toast.makeText(MainActivity.this, "booking",Toast.LENGTH_SHORT).show();break;
                    case R.id.myprofile:
                        Toast.makeText(MainActivity.this, "My Profile",Toast.LENGTH_SHORT).show();break;

                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;

                    case R.id.share:
                        Toast.makeText(MainActivity.this, "Share",Toast.LENGTH_SHORT).show();break;

                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Log out",Toast.LENGTH_SHORT).show();break;

                    default:
                        return true;
                }


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

        super.onBackPressed();

        finishAffinity();
    }
}
