package com.bilgiislem.sems.beunapp.DHE_Sources;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.DHE_Sources.GridViewSources.GridViewActivity;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class IcerikActivity extends AppCompatActivity {

    private static final String TAG_ICERIK = "icerik";
    String baslik_plus, http_plus, url, url_share;
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
        //url = http_plus;
        url_share = "http://w3.beun.edu.tr" + http_plus;
        //url_share = http_plus;
        setContentView(R.layout.activity_icerik);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(baslik_plus);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingData = (TextView) findViewById(R.id.loading_data);
        galleryButton = (Button) findViewById(R.id.gallery_button);
        webView = (WebView) findViewById(R.id.icerik_http_text);
        webView.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
                if (texticerik.startsWith("http://") || texticerik.startsWith("w3") || texticerik.startsWith("<p><a href=\"http://")) {
                    webView.loadUrl(texticerik);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(false);
                } else if (icerik.contains("type=\"application/pdf\"")) {
                    while (icerik.contains("type=\"application/pdf\"")) {
                        int startIndex = icerik.indexOf("<p><object style=");
                        int endIndex = icerik.indexOf("</object></p>");
                        Log.d("startEnd", "" + startIndex + "+" + endIndex);
                        String replaceStr = "";
                        String toBeReplaced = icerik.substring(startIndex, endIndex + 9);
                        icerik = icerik.replace(toBeReplaced, replaceStr);
                        Log.d("icerik", icerik);
                    }
                    if (icerik.contains("/dosyalar/")) {
                        String replacedicerik = icerik.replaceAll("../../../..", "http://w3.beun.edu.tr/");
                        webView.loadDataWithBaseURL(null, replacedicerik, "text/html", "utf-8", null);
                    } else {
                        webView.loadDataWithBaseURL(null, icerik, "text/html", "utf-8", null);
                    }
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
                webView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
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
                webView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, url_share);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, baslik_plus);
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.action_share)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}