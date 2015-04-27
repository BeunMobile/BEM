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

public class Menu6_Fragment_E_Kapmus extends Fragment {
    View rootview;
    WebView webView;
    String url_ekampus = "http://w3.beun.edu.tr/";
            //"https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";
    String data = "";
    Element div;
    Document doc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu6_layout_e_kampus, container, false);
        webView = (WebView) rootview.findViewById(R.id.ekampus_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_ekampus);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "E-Kampüs Sayfanız Yukleniyor...", Toast.LENGTH_LONG).show();

        ThreadparsingUrl.start();

        startWebView(url_ekampus);

        return rootview;
    }


    private void startWebView(String url) {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Yükleniyor...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                        webView.loadData(data, "text/html", null);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        webView.loadUrl(url);


    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            onBackPressed();
        }
    }

    Thread ThreadparsingUrl = new Thread() {
        public void run() {
            try {
                doc = Jsoup.connect(url_ekampus).get();
                div = doc.select("#yazilar").get(0);
                data += div;
                Log.i("Words", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


}
