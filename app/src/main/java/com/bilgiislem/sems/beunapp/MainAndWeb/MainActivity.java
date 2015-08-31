package com.bilgiislem.sems.beunapp.MainAndWeb;


import android.app.AlertDialog;
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

import com.bilgiislem.sems.beunapp.AkademikTakvim.Akademik_Fragment;
import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Duyurular_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Haberler_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Etkinlikler_Fragment;
import com.bilgiislem.sems.beunapp.KampusGorunumu.Kampus_Fragment;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.Yemek_Fragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isOnline()) {
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

        android.support.v4.app.Fragment objFragment = null;
        objFragment = new AnaSayfa_Fragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                android.support.v4.app.Fragment objFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.item_anasayfa:
                        objFragment = new AnaSayfa_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_duyurular:
                        objFragment = new Duyurular_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_haberler:
                        objFragment = new Haberler_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_etkinliktakvimi:
                        objFragment = new Etkinlikler_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_yemeklistesi:
                        objFragment = new Yemek_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_akademik:
                        objFragment = new Akademik_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.item_ekampus:
                        Intent intent_ekampus = new Intent(MainActivity.this, WebActivity.class);
                        intent_ekampus.putExtra("web", "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f");
                        startActivity(intent_ekampus);
                        return true;
                    case R.id.item_eposta:
                        Intent intent_eposta = new Intent(MainActivity.this, WebActivity.class);
                        intent_eposta.putExtra("web", "http://stu.karaelmas.edu.tr/sm/src/login.php");
                        startActivity(intent_eposta);
                        return true;
                    case R.id.item_uzem:
                        Intent intent_uzem = new Intent(MainActivity.this, WebActivity.class);
                        intent_uzem.putExtra("web", "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f");
                        startActivity(intent_uzem);
                        return true;
                    case R.id.item_kampusgorunumu:
                        objFragment = new Kampus_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
                        return true;
                    default:
                        objFragment = new AnaSayfa_Fragment();
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame, objFragment).addToBackStack(null).commit();
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
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

}
