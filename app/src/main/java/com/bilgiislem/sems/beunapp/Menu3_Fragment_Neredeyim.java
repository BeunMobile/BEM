package com.bilgiislem.sems.beunapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu3_Fragment_Neredeyim extends Fragment{
    View rootview;
    private String Url ="http://w3.beun.edu.tr/dosyalar/genel/nisan-yemek.pdf";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu3_layout, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.yemekhane_page);
        webView.loadUrl(Url);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(50);
        return rootview;
    }
}
