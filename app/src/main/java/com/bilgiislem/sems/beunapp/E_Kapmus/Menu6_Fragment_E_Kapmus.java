package com.bilgiislem.sems.beunapp.E_Kapmus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class Menu6_Fragment_E_Kapmus extends Fragment {
    View rootview;
    private String Url_Yemek = "http://w3.beun.edu.tr/dosyalar/genel/nisan-yemek.pdf";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu6_layout_e_kampus, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.ekampus_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + Url_Yemek);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "Yemek Listesi Yukleniyor...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}
