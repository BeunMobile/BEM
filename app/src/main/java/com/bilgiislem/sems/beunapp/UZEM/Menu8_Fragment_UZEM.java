package com.bilgiislem.sems.beunapp.UZEM;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class Menu8_Fragment_UZEM extends Fragment {
    View rootview;
    private String url_uzem = "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu8_layout_uzem, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.uzem_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_uzem);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "UZEM Sayfanız Yukleniyor...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}
