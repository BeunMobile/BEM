package com.bilgiislem.sems.beunapp.DHE_Sources;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.DHE_Sources.GridViewSources.GridViewActivity;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;


public class IcerikActivity extends AppCompatActivity {

    private static final String TAG_ICERIK = "icerik";
    String baslik_plus, http_plus, url;
    WebView webView;
    Button galleryButton;
    TextView loadingData;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baslik_plus = getIntent().getStringExtra("baslik");
        http_plus = getIntent().getStringExtra("adres");
        url = "http://w3.beun.edu.tr/veri" + http_plus;
        setContentView(R.layout.icerik_layout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(baslik_plus);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingData = (TextView) findViewById(R.id.loading_data);
        galleryButton = (Button) findViewById(R.id.gallery_button);
        webView = (WebView) findViewById(R.id.icerik_http_text);
        webView.setVisibility(View.GONE);
        galleryButton.setVisibility(View.GONE);
        new JSONParse().execute();
    }


    private class JSONParse extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                String icerik = jsonObj.getString(TAG_ICERIK);
                if (!jsonObj.getJSONArray("fotograf").toString().equals("[]")) {
                    galleryButton.setVisibility(View.VISIBLE);
                    galleryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent galleryintent = new Intent(IcerikActivity.this, GridViewActivity.class);
                            galleryintent.putExtra("url", url);
                            galleryintent.putExtra("baslik", baslik_plus);
                            startActivity(galleryintent);
                        }
                    });
                }
                String texticerik = html2text(icerik);
                if (texticerik.startsWith("http://") || texticerik.startsWith("w3")) {
                    webView.loadUrl(texticerik);

                } else if (icerik.contains("/dosyalar/")) {
                    String replacedicerik = icerik.replaceAll("../../../..", "http://w3.beun.edu.tr/");
                    webView.loadDataWithBaseURL(null, replacedicerik, "text/html", "utf-8", null);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new MyWebViewClient());
                } else {
                    webView.loadDataWithBaseURL(null, icerik, "text/html", "utf-8", null);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new MyWebViewClient());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
            }
        }


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


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}