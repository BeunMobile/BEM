package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;


public class AnaSayfa_WebPage extends Activity {
    WebView mWeb;
    ProgressDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mWeb = new WebView(this);
        setContentView(mWeb);
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWeb.setInitialScale(100);
        settings.setBuiltInZoomControls(true);
        mProgress = ProgressDialog.show(this, this.getString(R.string.loading), this.getString(R.string.waitfor));
        mProgress.setCancelable(true);
        mWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (mProgress.isShowing()) {
                    Log.i("onPageFinished", "Page is just finished.");
                    mProgress.dismiss();
                }
            }
        });
        mWeb.loadUrl("http://w3.beun.edu.tr");
    }

    @Override
    public void onBackPressed() {
        if (mWeb.canGoBack())
            mWeb.goBack();
        else
            super.onBackPressed();
    }
}