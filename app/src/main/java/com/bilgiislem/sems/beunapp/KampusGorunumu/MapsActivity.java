package com.bilgiislem.sems.beunapp.KampusGorunumu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {

    private GoogleMap googleMaps;
    String marker_title;
    LocationManager lm;
    String maps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        maps = getIntent().getStringExtra("maps");
        setUpMapIfNeeded(maps);
        googleMaps.setOnInfoWindowClickListener(this);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isNetwork && !isGPS) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.location_decision);

            alertDialogBuilder.setPositiveButton("Hayýr,Teþekkürler.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MapsActivity.this, R.string.location, Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Evet.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(maps);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpFarabi()} once when {@link #googleMaps} is not null.
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
    private void setUpMapIfNeeded(String maps) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMaps == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMaps = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_maps))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (googleMaps != null) {
                switch (maps) {
                    case "farabi":
                        setUpFarabi();
                        break;
                    case "ibni":
                        setUpIbni();
                        break;
                    default:
                        setUpFarabi();
                        break;
                }

            }
        }
    }

    private void setUpFarabi() {
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.450812, 31.761550)).title(getResources().getString(R.string.farabi_bidb)).snippet(getResources().getString(R.string.farabi_3d)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.451392, 31.763058)).title(getResources().getString(R.string.farabi_rektorluk)).snippet(getResources().getString(R.string.farabi_3d)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.450396, 31.762356)).title(getResources().getString(R.string.farabi_eem)).snippet(getResources().getString(R.string.farabi_3d)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.450911, 31.761371)).title(getResources().getString(R.string.farabi_sem)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.450058, 31.761069)).title(getResources().getString(R.string.farabi_maden)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.449758, 31.762388)).title(getResources().getString(R.string.farabi_makina)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.450581, 31.760402)).title(getResources().getString(R.string.farabi_insaat)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.451744, 31.762187)).title(getResources().getString(R.string.farabi_restoran)).snippet(getResources().getString(R.string.farabi_3d)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.451014, 31.760503)).title(getResources().getString(R.string.farabi_spor)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.451033, 31.763069)).title(getResources().getString(R.string.farabi_ust)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.449085, 31.763491)).title(getResources().getString(R.string.farabi_alt)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.451188, 31.761904)).title(getResources().getString(R.string.farabi_muhdekan)).snippet(getResources().getString(R.string.farabi_3d)));
        googleMaps.addMarker(new MarkerOptions().position(new LatLng(41.454093, 31.764503)).title(getResources().getString(R.string.farabi_giris)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(41.450777, 31.762411)).zoom(17).tilt(50).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMaps.animateCamera(cameraUpdate);
        googleMaps.setMyLocationEnabled(true);
        googleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Toast.makeText(this, getString(R.string.farabi_title), Toast.LENGTH_SHORT).show();
    }

    private void setUpIbni() {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(41.4141777, 31.7042748)).zoom(18).tilt(50).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMaps.animateCamera(cameraUpdate);
        googleMaps.setMyLocationEnabled(true);
        googleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Toast.makeText(this, getString(R.string.ibni_title), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker_title = marker.getTitle();
        Intent intent = new Intent(MapsActivity.this, SynthActivity.class);
        switch (marker_title) {
            case "Bilgi Ýþlem Daire Baþkanlýðý":
                intent.putExtra("beu3d", "bidb");
                startActivity(intent);
                break;
            case "Rektörlük Binasý":
                intent.putExtra("beu3d", "rektor");
                startActivity(intent);
                break;
            case "Elektrik Elektronik Mühendisliði":
                intent.putExtra("beu3d", "eem");
                startActivity(intent);
                break;
            case "BEÜN Misafirhane-Restoran":
                intent.putExtra("beu3d", "misafir");
                startActivity(intent);
                break;
            case "Mühendislik Fakültesi Dekanlýðý":
                intent.putExtra("beu3d", "muhdekan");
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.beu3d_none, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
