package com.bilgiislem.sems.beunapp.NavigationAndMain;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Duyurular_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Haberler_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Etkinlikler_Fragment;
import com.bilgiislem.sems.beunapp.EEU.EKampus_Fragment;
import com.bilgiislem.sems.beunapp.EEU.EPosta_Fragment;
import com.bilgiislem.sems.beunapp.EEU.Uzem_Fragment;
import com.bilgiislem.sems.beunapp.KampusGorunumu.Kampus_Fragment;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.BlankFragments.Blank_Fragment;
import com.bilgiislem.sems.beunapp.BlankFragments.Blank_FragmentV4;
import com.bilgiislem.sems.beunapp.YemekListesi.Yemek_Fragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isOnline() != true) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.connection_decision);

            alertDialogBuilder.setPositiveButton("Hayýr,Teþekkürler.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, R.string.connection, Toast.LENGTH_LONG).show();//
                }
            });

            alertDialogBuilder.setNegativeButton("Evet.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        android.app.Fragment objFragment = null;
        android.app.FragmentManager fragmentManager = getFragmentManager();
        objFragment = new AnaSayfa_Fragment();
        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                Fragment objFragment = null;
                android.support.v4.app.Fragment objFragment2 = null;
                android.app.FragmentManager fragmentManager = getFragmentManager();
                switch (menuItem.getItemId()) {
                    case R.id.item_anasayfa:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.ana_sayfa_title);
                        objFragment = new AnaSayfa_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_duyurular:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.duyuru_title);
                        objFragment = new Duyurular_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_haberler:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.haber_title);
                        objFragment = new Haberler_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_etkinliktakvimi:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.etkinlik_title);
                        objFragment = new Etkinlikler_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_yemeklistesi:
                        objFragment = new Blank_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        toolbar.setTitle(R.string.yemek_title);
                        objFragment2 = new Yemek_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        return true;
                    case R.id.item_ekampus:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.e_kampus_title);
                        objFragment = new EKampus_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_eposta:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.e_posta_title);
                        objFragment = new EPosta_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_uzem:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.uzem_title);
                        objFragment = new Uzem_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    case R.id.item_kampusgorunumu:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.kampus_title);
                        objFragment = new Kampus_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;
                    default:
                        objFragment2 = new Blank_FragmentV4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment2).commit();
                        toolbar.setTitle(R.string.ana_sayfa_title);
                        objFragment = new AnaSayfa_Fragment();
                        fragmentManager.beginTransaction().replace(R.id.frame, objFragment).commit();
                        return true;

                }

            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.appexit_decision);

            alertDialogBuilder.setPositiveButton("Hayýr,Teþekkürler.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

            alertDialogBuilder.setNegativeButton("Evet.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
