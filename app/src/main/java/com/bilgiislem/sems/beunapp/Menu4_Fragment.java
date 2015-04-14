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
public class Menu4_Fragment extends Fragment {
    View rootview;
    private String Url="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu4_layout, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.ekampus_page);
        webView.loadUrl(Url);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        return rootview;
    }
}
