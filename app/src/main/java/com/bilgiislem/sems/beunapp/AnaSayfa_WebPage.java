package com.bilgiislem.sems.beunapp;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;


public class AnaSayfa_WebPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String AnaUrl="http://w2.beun.edu.tr/";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa__web_page);
        WebView webview = (WebView) findViewById(R.id.ana_sayfa_wp);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(AnaUrl);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setInitialScale(50);
        Toast.makeText(this, "Ana Sayfa yükleniyor...", Toast.LENGTH_LONG);
        setTitle("Bülent Ecevit Üniversitesi Ana Sayfa");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ana_sayfa__web_page, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}