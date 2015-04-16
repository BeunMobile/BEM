package com.bilgiislem.sems.beunapp.Neredeyim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FarabiActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    LocationManager lm;
    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farabi);
        setUpMapIfNeeded();

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isNetwork && !isGPS) {
            new AlertDialog.Builder(this)
                    .setTitle("Lokasyon")
                    .setMessage("Konum ayarlariniz kapali acilsin mi?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.farabi_map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.450812, 31.761550)).title("Bilgi Islem Daire Baskanligi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.451392, 31.763058)).title("Rektorluk"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.450396, 31.762356)).title("Elektrik Elektronik Muhendisligi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.450911, 31.761371)).title("BEU Sem"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.450058, 31.761069)).title("Maden Muhendisligi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.449758, 31.762388)).title("Makine Muhendisligi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.450581, 31.760402)).title("Insaat Muhendisligi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.451744, 31.762187)).title("Misafirhane-Restoran"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.451014, 31.760503)).title("Spor Salonu"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.451033, 31.763069)).title("Ust Kapi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.449085, 31.763491)).title("Alt Kapi"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.454093, 31.764503)).title("BEU Giris"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(41.450777, 31.762411)).zoom(17).tilt(25).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //animate camera kameray� hareket ettirme animasyonlu bir �ekilde .move camera dedi�imizde animasyonsuz h�zl� bir �ekilde orada olur .
        mMap.animateCamera(cameraUpdate);
        Toast.makeText(this, "Farabi Kampusu", Toast.LENGTH_SHORT).show();

    }
}
