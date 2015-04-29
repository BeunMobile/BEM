package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bilgiislem.sems.beunapp.R;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Menu2_Fragment_Duyurular extends Fragment {
    Document doc;
    String url_duyurular = "http://w3.beun.edu.tr/";
    String data = "";
    Element div;
    private ProgressBar duyurular_pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);
        final WebView webView = (WebView) rootview.findViewById(R.id.duyurular_page);
        duyurular_pb=(ProgressBar) rootview.findViewById(R.id.duyurular_progress);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        ThreadparsingUrl.start();
        webView.setWebViewClient(new WebViewClient() {
            /*
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                duyurular_pb.setVisibility(View.VISIBLE);
                duyurular_pb.setProgress(progress);
                if (progress==100){
                    duyurular_pb.setVisibility(View.GONE);
                }

            }
            */
            @Override
            public void onPageFinished(WebView view, String url) {
                duyurular_pb.setVisibility(View.VISIBLE);
                webView.loadData(data, "text/html; charset=utf-8", "utf-8");
            }
        });


        webView.loadUrl(url_duyurular);

        return rootview;
    }

    Thread ThreadparsingUrl = new Thread() {
        public void run() {
            try {
                doc = Jsoup.connect(url_duyurular).get();
                div = doc.select("#yazilar").get(0);
                data += div;
                Log.i("Words", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}