package com.bilgiislem.sems.beunapp.EEU;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

public class EKampus_Fragment extends Fragment {
    WebView webView;
    String url_ekampus = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";
    private Bundle webViewBundle;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_webpage, container, false);
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
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });


        // Just load whatever URL this fragment is
        // created with.
        return view;
    }

    // This is the method the pager adapter will use
    // to create a new fragment
    public static Fragment newInstance(String url) {
        EKampus_Fragment f = new EKampus_Fragment();
        f.url_ekampus = url;
        return f;
    }

    @Override
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (webViewBundle != null) {
            webView.restoreState(webViewBundle);
        }
    }

    private boolean webViewGoBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

}