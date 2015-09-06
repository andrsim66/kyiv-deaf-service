package com.smartcity.kyivdeafservice.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.smartcity.kyivdeafservice.app.R;

/**
 * Created by voronsky on 06.09.15.
 */
public class MapActivity extends FragmentActivity {

    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        googleMap = supportMapFragment.getMap();
    }
}
