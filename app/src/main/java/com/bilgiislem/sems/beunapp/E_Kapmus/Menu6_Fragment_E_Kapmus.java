package com.bilgiislem.sems.beunapp.E_Kapmus;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Menu6_Fragment_E_Kapmus  extends Fragment {
    View rootview;
    private String url_uzem = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu8_layout_uzem, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.uzem_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_uzem);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), this.getString(R.string.ekampus), Toast.LENGTH_LONG).show();
        return rootview;
    }
}