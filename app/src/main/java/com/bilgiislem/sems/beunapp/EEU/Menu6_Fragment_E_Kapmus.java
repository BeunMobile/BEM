package com.bilgiislem.sems.beunapp.EEU;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

public class Menu6_Fragment_E_Kapmus extends Fragment {
    WebView webView;
    String url_ekampus = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";
    private Bundle webViewBundle;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.web_page_layout, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);

        webView = (WebView) view.findViewById(R.id.web_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f");

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
            }
        });
        webView.loadUrl(url_ekampus);

        // Just load whatever URL this fragment is
        // created with.
        return view;
    }

    // This is the method the pager adapter will use
    // to create a new fragment
    public static Fragment newInstance(String url) {
        Menu6_Fragment_E_Kapmus f = new Menu6_Fragment_E_Kapmus();
        f.url_ekampus = url;
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

}