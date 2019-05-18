package com.travel.iti.travelapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.travel.iti.travelapp.R;

import java.util.HashMap;

public class PackageDetailsActivity extends AppCompatActivity  {
    CarouselView carouselView;
    CarouselView customCarouselView;

    HashMap<String,String> url_maps;
    TextView carouselLabel;
    TextView customCarouselLabel;
    String[] sampleNetworkImageURLs = {
            "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg",
            "http://cdn3.nflximg.net/images/3093/2043093.jpg",
            "http://tvfiles.alphacoders.com/100/hdclearart-10.png",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        carouselView.setPageCount(sampleNetworkImageURLs.length);
        carouselView.setImageListener(imageListener);

    }
    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).
                    placeholder(R.drawable.wetravel).error(R.drawable.wetravel).fit().centerCrop().into(imageView);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }
}
