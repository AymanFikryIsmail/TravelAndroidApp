package com.travel.iti.travelapp.view.activity.home;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.travel.iti.travelapp.R;
//import com.travel.iti.travelapp.view.adapter.RecyclerViewAdapter;

import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.view.activity.home.fragments.booking.BookingsFragment;
import com.travel.iti.travelapp.view.activity.home.fragments.favourite.FavoritesFragment;
import com.travel.iti.travelapp.view.activity.home.main.MainFragment;
import com.travel.iti.travelapp.view.activity.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private EditText searchDestinationEditText ;
    TextView userName , email;
    private PrefManager prefManager;
    private Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager=new PrefManager(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        toggle.setDrawerIndicatorEnabled(true);
      //  toolbar = findViewById(R.id.activity_main);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView)findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
         userName=navHeader.findViewById(R.id.user_name_id);
        email=navHeader.findViewById(R.id.emmailId);
        userName.setText(prefManager.getUserData().getUsername());
        email.setText(prefManager.getUserData().getEmail());

        MainFragment mainFragment= new MainFragment();
        loadFragment(mainFragment,"Home");

    }
    private void loadFragment(Fragment fragment, String barTitle){
        setTitle(barTitle);

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
                if (doubleBackToExitPressedOnce) {
                    finish();
                }
                loadFragment( MainFragment.newInstance(),"Home");

                this.doubleBackToExitPressedOnce = true;

            }
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.home:
                //MainFragment mainFragment= new MainFragment();
                loadFragment(MainFragment.newInstance(),"Home ");
                break;
            case R.id.favorites:

              //  FavoritesFragment favoritesFragment= new FavoritesFragment();
                loadFragment(FavoritesFragment.newInstance(),"My Favorites ");
                break;

            case R.id.booking:
               // BookingsFragment bookingsFragment= new BookingsFragment();
                loadFragment(BookingsFragment.newInstance(),"My Bookings ");
                break;

//            case R.id.settings:
//                loadFragment(SettingsFragment.newInstance(),"favoritesFragment ");
//                break;
            case R.id.logout:
                Toast.makeText(MainActivity.this, "Log out",Toast.LENGTH_SHORT).show();
                prefManager.setUserId(0);

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
