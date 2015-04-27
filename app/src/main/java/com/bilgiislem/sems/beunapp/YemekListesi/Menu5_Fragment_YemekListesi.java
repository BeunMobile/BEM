package com.bilgiislem.sems.beunapp.YemekListesi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu5_Fragment_YemekListesi extends Fragment {
    View rootview;
    private String url_yemek = "http://w3.beun.edu.tr/dosyalar/genel/nisan-yemek.pdf";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu5_layout_yemeklistesi, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.yemek_page);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url_yemek);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "Yemek Listesi Yukleniyor...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}
