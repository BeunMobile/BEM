package com.bilgiislem.sems.beunapp.Duyurular;


import android.app.Fragment;
import android.app.ProgressDialog;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Menu2_Fragment_Duyurular extends Fragment {
    View rootview;
    Document doc;
    String url_duyurular = "http://w3.beun.edu.tr/";
    String data = "";
    Element div;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);
        final WebView webView = (WebView) rootview.findViewById(R.id.duyurular_page);
        webView.getSettings().setJavaScriptEnabled(true);
        ThreadparsingUrl.start();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadData(data, "text/html", null);
            }
        });

        webView.loadUrl(url_duyurular);

        return rootview;
    }


        Thread ThreadparsingUrl = new Thread() {
            public void run() {
                try {
                    doc = Jsoup.connect(url_duyurular).get();
                    div = doc.select("#yazilar").get(1);
                    data += div;
                    Log.i("Words", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

}