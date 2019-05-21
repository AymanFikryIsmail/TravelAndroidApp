package com.travel.iti.travelapp.view.activity._package;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.model.PackagesPojo;
import com.travel.iti.travelapp.view.adapter.PackagesAdapter;
import com.travel.iti.travelapp.viewmodel.PackagesViewModel;
import java.util.ArrayList;
import java.util.List;

public class PackageActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
    private List<PackagesPojo> packagesPojoList;
    private RecyclerView recyclerView;
    private PackagesAdapter packagesAdapter;
    private PackagesViewModel packagesViewModel;

    TextView customCarouselLabel;
    String[] sampleNetworkImageURLs = {
            "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg",
            "http://cdn3.nflximg.net/images/3093/2043093.jpg",
            "http://tvfiles.alphacoders.com/100/hdclearart-10.png",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg",
    };

    public PackageActivity() {
        packagesPojoList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        sliderLayout = findViewById(R.id.packageImageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
        setSliderViews();

        packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);
        packagesViewModel.init(getApplicationContext());

        recyclerView = findViewById(R.id.packages_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        packagesAdapter = new PackagesAdapter(getApplicationContext(), packagesPojoList);
        packagesPojoList = null;
        getData();
    }


    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ic_launcher_background);
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("The quick brown fox jumps over the lazy dog.\n" +
                    "Jackdaws love my big sphinx of quartz. " + (i + 1));
            final int finalI = i;

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(PackageActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    void getData(){
        packagesViewModel.getData();
        packagesViewModel.packagesData.observe(this, new Observer<List<PackagesPojo>>() {
            @Override
            public void onChanged(@Nullable List<PackagesPojo> packagesPojos) {
                packagesPojoList = packagesPojos;
                packagesAdapter.updateList(packagesPojoList);
                recyclerView.setAdapter(packagesAdapter);
            }
        });
    }
}
