package com.bilgiislem.sems.beunapp.EtkinlikTakvimi;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class Menu4_Fragment_Etkinlik_Takvimi extends Fragment {
    Document doc;
    String url_duyurular = "http://w3.beun.edu.tr/";
    String data = "";
    Element div;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu4_layout_etkinlik_takvimi, container, false);
        final WebView webView = (WebView) rootview.findViewById(R.id.etkinlik_takvimi_page);
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
                div = doc.select("#yazilar").get(2);
                data += div;
                Log.i("Words", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}