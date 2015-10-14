package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.R;

public class WebActivity extends AppCompatActivity {

    String url;
    WebView webView;
    TextView loadingData, noConnection;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("web");
        setContentView(R.layout.activity_web);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        setSupportActionBar(toolbar);
        switch (url) {
            case "http://w3.beun.edu.tr/":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_section1));
                break;
            case "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_section7));
                break;
            case "http://stu.karaelmas.edu.tr/sm/src/login.php":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_section8));
                break;
            case "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_section9));
                break;
            case "https://docs.google.com/gview?embedded=true&url=http://w3.beun.edu.tr/dosyalar/ana_sayfa/ekim2015.pdf":
                getSupportActionBar().setTitle(getResources().getString(R.string.monthly_list));
                break;
            default:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_section1));
                break;
        }
        loadingData = (TextView) findViewById(R.id.loading_data);
        noConnection = (TextView) findViewById(R.id.empty_data);
        webView = (WebView) findViewById(R.id.icerik_http_text);
        webView.setVisibility(View.GONE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setInitialScale(80);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (!isOnline()) {
                    loadingData.setVisibility(View.GONE);
                    noConnection.setVisibility(View.VISIBLE);
                } else {
                    loadingData.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}