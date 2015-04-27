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
    private String url_ekampus = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu6_layout_e_kampus, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.ekampus_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_ekampus);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "Yemek Listesi Yukleniyor...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}
