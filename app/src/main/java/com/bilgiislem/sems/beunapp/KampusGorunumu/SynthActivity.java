package com.bilgiislem.sems.beunapp.KampusGorunumu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class SynthActivity extends Activity {
    WebView webview;
    String Url_3D;
    String check_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webpage);
        check_marker = getIntent().getStringExtra("beu3d");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        switch (check_marker) {
            case "bidb":
                Url_3D = "https://photosynth.net/preview/embed/3646a8b7-4660-42f5-b2a7-9f88476d42ee?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(SynthActivity.this, SynthActivity.this.getResources().getString(R.string.beu3d_bidb), Toast.LENGTH_LONG).show();
                break;
            case "rektor":
                Url_3D = "https://photosynth.net/preview/embed/c1a4d3f4-86fe-42db-b334-dc1b63489e4f?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(SynthActivity.this, SynthActivity.this.getResources().getString(R.string.beu3d_rektorluk), Toast.LENGTH_LONG).show();
                break;
            case "eem":
                Url_3D = "https://photosynth.net/preview/embed/57cf6133-e2e3-4274-9c75-b7e3f7a773f3?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(SynthActivity.this, SynthActivity.this.getResources().getString(R.string.beu3d_eem), Toast.LENGTH_LONG).show();
                break;
            case "misafir":
                Url_3D = "https://photosynth.net/preview/embed/e10a60ec-dcd6-4e42-b3c9-09220e5a9c25?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(SynthActivity.this, SynthActivity.this.getResources().getString(R.string.beu3d_eem), Toast.LENGTH_LONG).show();
                break;
            case "muhdekan":
                Url_3D = "https://photosynth.net/preview/embed/49b1156f-5128-4e0f-895d-1b6e9cba3236?delayload=false&autoplay=true&fromsite=true";
                Toast.makeText(SynthActivity.this, SynthActivity.this.getResources().getString(R.string.beu3d_eem), Toast.LENGTH_LONG).show();
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