package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    WebView webView;
    String url_duyurular = "http://w3.beun.edu.tr/";
    ProgressBar progressBar_duyurular;
    private Bundle webViewBundle;
    String data = "";
    Element div;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);

        progressBar_duyurular = (ProgressBar) view.findViewById(R.id.duyurular_progress);
        webView = (WebView) view.findViewById(R.id.duyurular_page);
        webView.getSettings().setJavaScriptEnabled(true);
        ThreadparsingUrl.start();
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl(url_duyurular);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                webView.loadUrl(url);
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);

                progressBar_duyurular.setVisibility(View.GONE);
                webView.loadData(data, "text/html; charset=utf-8", "utf-8");
            }
        });
        webView.loadUrl(url_duyurular);

        // Just load whatever URL this fragment is
        // created with.
        return view;
    }

    // This is the method the pager adapter will use
    // to create a new fragment
    public static Fragment newInstance(String url) {
        Menu2_Fragment_Duyurular f = new Menu2_Fragment_Duyurular();
        f.url_duyurular = url;
        return f;
    }

    /**
     * Sla webview op
     */
    @Override
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    /**
     * Herstel staat van webview
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (webViewBundle != null) {
            webView.restoreState(webViewBundle);
        }
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