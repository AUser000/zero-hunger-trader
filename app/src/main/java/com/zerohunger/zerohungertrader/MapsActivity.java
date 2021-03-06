package com.zerohunger.zerohungertrader;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public double lat;
    public double lng;

    public Intent intent;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //me.setPosition( mMap.getCenter());
        //mMap.addMarker(new MarkerOptions().draggable(true).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.75, 80.7), 8));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.style));
            if (!success) {
                //Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            //Log.e(TAG, "Can't find style. Error: ", e);
        }


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng location = mMap.getCameraPosition().target;
                MarkerOptions marker = new MarkerOptions().position(location).title("");
                mMap.clear();
                mMap.addMarker(marker);
                lat = location.latitude;
                lng = location.longitude;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
            }
        });

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.putExtra("LAT", lat);
                intent.putExtra("LNG", lng);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }


}
