package com.bilgiislem.sems.beunapp.Beu3D;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

import com.bilgiislem.sems.beunapp.R;

public class Beu_3DPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beu_3dpage_layout);
        WebView webview = (WebView) findViewById(R.id.beu_3d_page);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://photosynth.net/preview/embed/283b433e-508f-41bd-b385-1a011d358a6c?delayload=false&autoplay=true&fromsite=true");
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
