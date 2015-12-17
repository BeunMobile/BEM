package com.bilgiislem.sems.beunapp.MainActivity;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.AkademikTakvim.Akademik_Fragment;
import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_Fragment;
import com.bilgiislem.sems.beunapp.AnaSayfa.WebActivity;
import com.bilgiislem.sems.beunapp.DHE.Duyurular_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Etkinlikler_Fragment;
import com.bilgiislem.sems.beunapp.DHE.Haberler_Fragment;
import com.bilgiislem.sems.beunapp.Hakkinda.Hakkinda_Fragment;
import com.bilgiislem.sems.beunapp.Hakkinda.Iletisim_Fragment;
import com.bilgiislem.sems.beunapp.KampusGorunumu.Kampus_Fragment;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.YemekTab_Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    CircleImageView facebook, twitter, instagram, youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View headerLayout =
                navigationView.inflateHeaderView(R.layout.item_header);

        facebook = (CircleImageView) headerLayout.findViewById(R.id.facebook_image);
        twitter = (CircleImageView) headerLayout.findViewById(R.id.twitter_image);
        instagram = (CircleImageView) headerLayout.findViewById(R.id.instagram_image);
        youtube = (CircleImageView) headerLayout.findViewById(R.id.youtube_image);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.toast_facebook, Toast.LENGTH_SHORT).show();
                openFacebookApp();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.toast_twitter, Toast.LENGTH_SHORT).show();
                openTwitterApp();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.toast_instagram, Toast.LENGTH_SHORT).show();
                openInstagramApp();
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.toast_youtube, Toast.LENGTH_SHORT).show();
                openYoutubeApp();
            }
        });

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
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_duyurular:
                                                                         objFragment = new Duyurular_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_haberler:
                                                                         objFragment = new Haberler_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_etkinliktakvimi:
                                                                         objFragment = new Etkinlikler_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_yemeklistesi:
                                                                         objFragment = new YemekTab_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_akademik:
                                                                         objFragment = new Akademik_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                                                                 .replace(R.id.frame, objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_ogrenci:
                                                                         Intent intent_ogrenci = new Intent(MainActivity.this, WebActivity.class);
                                                                         intent_ogrenci.putExtra("web", "http://ogrenci.beun.edu.tr/");
                                                                         startActivity(intent_ogrenci);
                                                                         return true;
                                                                     case R.id.item_ekampus:
                                                                         Intent intent_ekampus = new Intent(MainActivity.this, WebActivity.class);
                                                                         intent_ekampus.putExtra("web",
                                                                                 "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f");
                                                                         startActivity(intent_ekampus);
                                                                         return true;
                                                                     case R.id.item_eposta:
                                                                         android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog
                                                                                 .Builder(MainActivity.this);
                                                                         builder.setTitle(R.string.title_eposta);
                                                                         builder.setItems(R.array.e_mails, new DialogInterface.OnClickListener() {
                                                                             public void onClick(DialogInterface dialog, int item) {
                                                                                 switch (item) {
                                                                                     case 0:
                                                                                         Intent intent_eposta = new Intent(MainActivity.this, WebActivity.class);
                                                                                         intent_eposta.putExtra("web", "http://stu.karaelmas.edu.tr/sm/src/login.php");
                                                                                         startActivity(intent_eposta);
                                                                                         break;
                                                                                     case 1:
                                                                                         Intent intent_eposta1 = new Intent(MainActivity.this, WebActivity.class);
                                                                                         intent_eposta1.putExtra("web", "https://posta.beun.edu.tr/");
                                                                                         startActivity(intent_eposta1);
                                                                                         break;
                                                                                     default:
                                                                                         Log.e("Eposta", "Error");
                                                                                 }
                                                                             }
                                                                         });
                                                                         android.support.v7.app.AlertDialog alert = builder.create();
                                                                         alert.show();
                                                                         return true;
                                                                     case R.id.item_uzem:
                                                                         Intent intent_uzem = new Intent(MainActivity.this, WebActivity.class);
                                                                         intent_uzem.putExtra("web", "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f");
                                                                         startActivity(intent_uzem);
                                                                         return true;
                                                                     case R.id.item_kampusgorunumu:
                                                                         objFragment = new Kampus_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame,
                                                                                 objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_contact:
                                                                         objFragment = new
                                                                                 Iletisim_Fragment();
                                                                         getSupportFragmentManager().
                                                                                 beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame,
                                                                                 objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     case R.id.item_about:
                                                                         objFragment = new
                                                                                 Hakkinda_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame,
                                                                                 objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                     default:
                                                                         objFragment = new AnaSayfa_Fragment();
                                                                         getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.abc_fade_in,
                                                                                 R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out).replace(R.id.frame,
                                                                                 objFragment).addToBackStack(null).commit();
                                                                         return true;
                                                                 }
                                                             }
                                                         }
        );
        drawerLayout = (DrawerLayout)

                findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the menu_navigation closes as we dont want anything to happen so we leave this blank

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the menu_navigation open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

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

    private void openFacebookApp() {
        String facebookUrl = "https://www.facebook.com/beunedutr";
        String facebookID = "339918329402303";
        try {
            int versionCode = this.getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

            if (!facebookID.isEmpty()) {
                // open the Facebook app using facebookID (fb://profile/facebookID or fb://page/facebookID)
                Uri uri = Uri.parse("fb://page/" + facebookID);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else if (versionCode >= 3002850 && !facebookUrl.isEmpty()) {
                // open Facebook app using facebook url
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                // Facebook is not installed. Open the browser
                Uri uri = Uri.parse(facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Facebook is not installed. Open the browser
            Uri uri = Uri.parse(facebookUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void openTwitterApp() {
        Intent twitter = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=564639889"));
            twitter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/beunedutr"));
        }
        this.startActivity(twitter);
    }

    private void openInstagramApp() {
        Uri uri = Uri.parse("http://instagram.com/_u/beunedutr");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/beunedutr")));
        }
    }

    private void openYoutubeApp() {
        String url = "https://www.youtube.com/user/beunedu/";
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
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
