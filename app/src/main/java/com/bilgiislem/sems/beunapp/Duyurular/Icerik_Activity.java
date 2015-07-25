package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilgiislem.sems.beunapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;


public class Icerik_Activity extends ActionBarActivity {
    private static final String TAG_ICERIK = "icerik";

    String baslik_plus, http_plus;
    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baslik_plus = getIntent().getStringExtra("key2");
        http_plus = getIntent().getStringExtra("key");

        url = "http://w3.beun.edu.tr/veri" + http_plus;

        getSupportActionBar().setTitle(baslik_plus);
        setContentView(R.layout.icerik_layout);

        webView = (WebView) findViewById(R.id.icerik_http_text);

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Icerik_Activity.this);
            pDialog.setTitle(getString(R.string.loading));
            pDialog.setMessage(getString(R.string.waitfor));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                String icerik = jsonObj.getString(TAG_ICERIK);
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

                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
        }


    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}

                    /*String urlpdf = "http://docs.google.com/gview?embedded=true&url=" + "http://w3.beun.edu.tr/dosyalar";
                    Matcher m = Pattern.compile(
                            Pattern.quote("/dosyalar")
                                    + "(.*?)"
                                    + Pattern.quote(".pdf")
                    ).matcher(icerik);
                    while (m.find()) {
                        String match = m.group(1);
                        urlpdf = urlpdf + match + ".pdf";
                        Log.i("Match", match);
                    }
                    Log.i("URLpdf", urlpdf);*/