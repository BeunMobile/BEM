package com.bilgiislem.sems.beunapp.E_Kapmus;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bilgiislem.sems.beunapp.R;

public class Menu6_Fragment_E_Kapmus extends Fragment {
    WebView webView;
    String url_ekampus = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";
    ProgressBar progressBar_ekampus;
    private Bundle webViewBundle;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.menu6_layout_e_kampus,
                container,
                false);

        progressBar_ekampus = (ProgressBar) view.findViewById(R.id.ekampus_progress);
        webView = (WebView) view.findViewById(R.id.ekampus_page);
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

                progressBar_ekampus.setVisibility(View.GONE);
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

    /*
    View rootview;
    private String url_ekampus = "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu8_layout_uzem, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.uzem_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_ekampus);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), this.getString(R.string.ekampus), Toast.LENGTH_LONG).show();
        return rootview;
    }
}*/