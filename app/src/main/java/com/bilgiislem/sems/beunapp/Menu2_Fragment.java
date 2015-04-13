package com.bilgiislem.sems.beunapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu2_Fragment extends Fragment{
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu2_layout, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.neredeyim_page);
        webView.loadUrl("http://w3.beun.edu.tr/dosyalar/genel/nisan-yemek.pdf");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        return rootview;
    }
}
