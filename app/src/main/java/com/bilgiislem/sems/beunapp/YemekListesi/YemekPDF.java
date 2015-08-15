package com.bilgiislem.sems.beunapp.YemekListesi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

/**
 * Created by detro on 15.08.2015.
 */
public class YemekPDF extends Activity {
    WebView mWeb;
    ProgressDialog mProgress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        String month = getIntent().getStringExtra("month");
        String year = getIntent().getStringExtra("year");

        Log.i("Month/Year", month + "/" + year);

        mWeb = new WebView(this);
        setContentView(mWeb);
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
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
                    mProgress.dismiss();
                }
            }
        });


        mWeb.loadUrl("https://docs.google.com/gview?embedded=true&url=" + "http://w3.beun.edu.tr/dosyalar/ana_sayfa/agustos15.pdf");
    }

    @Override
    public void onBackPressed() {
        if (mWeb.canGoBack())
            mWeb.goBack();
        else
            super.onBackPressed();
    }
}