package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;

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
        // no need to use title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set webview as main content only
        mWeb = new WebView(this);
        setContentView(mWeb);
        // set Javascript
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWeb.setInitialScale(100);
        settings.setBuiltInZoomControls(true);

        // the init state of progress dialog
        mProgress = ProgressDialog.show(this, this.getString(R.string.loading), this.getString(R.string.waitfor));

        // add a WebViewClient for WebView, which actually handles loading data from web
        mWeb.setWebViewClient(new WebViewClient() {
            // load url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // when finish loading page
            public void onPageFinished(WebView view, String url) {
                if (mProgress.isShowing()) {
                    mProgress.dismiss();
                }
            }
        });
        // set url for webview to load
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