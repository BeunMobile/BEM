package com.bilgiislem.sems.beunapp.KampusGorunumu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class Beu_3DPage extends Activity {
    WebView webview;
    String Url_3D;
    String check_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page_layout);
        check_marker = getIntent().getStringExtra("beu3d");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        switch (check_marker) {
            case "bidb":
                Url_3D = "https://photosynth.net/preview/embed/3646a8b7-4660-42f5-b2a7-9f88476d42ee?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(Beu_3DPage.this, Beu_3DPage.this.getResources().getString(R.string.beu3d_bidb), Toast.LENGTH_LONG).show();
                break;
            case "eem":
                Url_3D = "https://photosynth.net/preview/embed/84a60386-44c0-421e-b25d-d69a4500cc95?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(Beu_3DPage.this, Beu_3DPage.this.getResources().getString(R.string.beu3d_eem), Toast.LENGTH_LONG).show();
                break;
            case "rektor":
                Url_3D = "https://photosynth.net/preview/embed/c1a4d3f4-86fe-42db-b334-dc1b63489e4f?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(Beu_3DPage.this, Beu_3DPage.this.getResources().getString(R.string.beu3d_rektorluk), Toast.LENGTH_LONG).show();
                break;
            default:
                Log.e("Check Marker", "Error on check_marker,check out the check_marker value.It could be empty.");
                break;
        }

        webview = (WebView) findViewById(R.id.web_page);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Url_3D);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beu_3dpage, menu);
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