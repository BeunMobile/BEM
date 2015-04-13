package com.bilgiislem.sems.beunapp;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.nio.channels.WritableByteChannel;


public class Menu1_Fragment extends Fragment {

    private WebView webView;
    private String Url = "http://w2.beun.edu.tr";
    ProgressDialog mProgressDialog;

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        if (Url != null) {

            WebView webView = (WebView) rootview.findViewById(R.id.main_menu);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(Url);
            webView.setInitialScale(50);
        }

        return rootview;
    }

    public void updateUrl(String UpUrl){
        Url=UpUrl;
        WebView webView = (WebView) getView().findViewById(R.id.main_menu);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(UpUrl);
    }

}
