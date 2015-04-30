package com.bilgiislem.sems.beunapp.UZEM;

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

public class Menu8_Fragment_UZEM extends Fragment {
    WebView webView;
    String url_uzem = "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f";
    ProgressBar progressBar_uzem;
    private Bundle webViewBundle;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.menu8_layout_uzem,
                container,
                false);

        progressBar_uzem = (ProgressBar) view.findViewById(R.id.uzem_progress);
        webView = (WebView) view.findViewById(R.id.uzem_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl(url_uzem);

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

                progressBar_uzem.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(url_uzem);

        // Just load whatever URL this fragment is
        // created with.
        return view;
    }

    // This is the method the pager adapter will use
    // to create a new fragment
    public static Fragment newInstance(String url) {
        Menu8_Fragment_UZEM f = new Menu8_Fragment_UZEM();
        f.url_uzem = url;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu8_layout_uzem, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.uzem_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_uzem);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "UZEM Sayfanız Yukleniyor...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}
*/