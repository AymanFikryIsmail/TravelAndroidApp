package com.travel.iti.travelapp.view.activity.home;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travel.iti.travelapp.R;
//import com.travel.iti.travelapp.view.adapter.RecyclerViewAdapter;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.view.activity.home.fragments.booking.BookingsFragment;
import com.travel.iti.travelapp.view.activity.home.fragments.favourite.FavoritesFragment;
import com.travel.iti.travelapp.view.activity.home.main.MainFragment;
import com.travel.iti.travelapp.view.activity.home.fragments.MyProfileFragment;
import com.travel.iti.travelapp.view.activity.home.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private EditText searchDestinationEditText ;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager=new PrefManager(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        toggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView)findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);

        MainFragment mainFragment= new MainFragment();
        loadFragment(mainFragment,"mainFragment ");

    }
    private void loadFragment(Fragment fragment, String barTitle){
       // toolbar.setTitle(barTitle);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment,barTitle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

        @Override
    public void onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else {
                super.onBackPressed();
                finishAffinity();
            }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.home:
                MainFragment mainFragment= new MainFragment();
                loadFragment(mainFragment,"mainFragment ");
                break;
            case R.id.favorites:

                FavoritesFragment favoritesFragment= new FavoritesFragment();
                loadFragment(favoritesFragment,"favoritesFragment ");
                break;

            case R.id.booking:
                BookingsFragment bookingsFragment= new BookingsFragment();
                loadFragment(bookingsFragment,"favoritesFragment ");
                break;
            case R.id.myprofile:
                MyProfileFragment myProfileFragment= new MyProfileFragment();
                loadFragment(myProfileFragment,"favoritesFragment ");
                break;
            case R.id.settings:
                loadFragment(SettingsFragment.newInstance(),"favoritesFragment ");
                break;
            case R.id.logout:
                Toast.makeText(MainActivity.this, "Log out",Toast.LENGTH_SHORT).show();
                prefManager.setUserId(0);

                break;
            default:
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
