package com.bilgiislem.sems.beunapp.Beu3D;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class Beu_3DPage extends Activity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String Url_3D = "https://photosynth.net/preview/view/354a8940-5d76-4ead-a6fe-e67f4a6ab003?delayload=false&autoplay=true&fromsite=false";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beu_3dpage_layout);
        webview = (WebView) findViewById(R.id.beu_3d_page);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Url_3D);
        Toast.makeText(Beu_3DPage.this, Beu_3DPage.this.getResources().getString(R.string.beu3d), Toast.LENGTH_LONG).show();
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