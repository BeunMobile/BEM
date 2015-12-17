package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.R;

public class WebActivity extends AppCompatActivity {

    String url;
    WebView webView;
    TextView noConnection;
    Toolbar toolbar;
    ProgressBar progressLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("web");
        setContentView(R.layout.activity_web);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        progressLine = (ProgressBar) findViewById(R.id.progressLine);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        setSupportActionBar(toolbar);
        noConnection = (TextView) findViewById(R.id.empty_data);
        webView = (WebView) findViewById(R.id.icerik_http_text);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        switch (url) {
            case "http://w3.beun.edu.tr/":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_main));
                webView.setInitialScale(80);
                break;
            case "http://ogrenci.beun.edu.tr/":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_students));
                break;
            case "https://ekampus.beun.edu.tr/Yonetim/Kullanici/Giris?ReturnUrl=%2f":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_ekampus));
                webView.setInitialScale(80);
                break;
            case "http://stu.karaelmas.edu.tr/sm/src/login.php":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_eposta));
                webView.setInitialScale(80);
                break;
            case "https://posta.beun.edu.tr/":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_eposta));
                webView.setInitialScale(80);
                break;
            case "http://ue.beun.edu.tr/Account/Login?ReturnUrl=%2f":
                getSupportActionBar().setTitle(getResources().getString(R.string.title_eposta));
                webView.setInitialScale(80);
                break;
            case "http://w3.beun.edu.tr/dosyalar/ana_sayfa/aralik2015-1.pdf":
                getSupportActionBar().setTitle(getResources().getString(R.string.monthly_list));
                webView.setInitialScale(80);
                break;
            default:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_main));
                webView.setInitialScale(80);
                break;
        }
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.loadUrl(url);
        WebActivity.this.progressLine.setProgress(0);
        webView.setWebChromeClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (!isOnline()) {
                    webView.setVisibility(View.GONE);
                    progressLine.setVisibility(View.GONE);
                    noConnection.setVisibility(View.VISIBLE);
                } else {
                    progressLine.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setValue(int progress) {
        this.progressLine.setProgress(progress);
    }


    private class MyWebViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            WebActivity.this.setValue(newProgress);
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chrome_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl())));
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