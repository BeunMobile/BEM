package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.widget.TextView;


import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Menu2_Icerik_Activity extends ActionBarActivity {

    private static final String TAG_ICERIK = "icerik";

    String baslik_plus, http_plus;
    TextView textView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baslik_plus = getIntent().getStringExtra("key2");
        http_plus = getIntent().getStringExtra("key");
        url = "http://w3.beun.edu.tr/veri" + http_plus;

        getSupportActionBar().setTitle(baslik_plus);
        setContentView(R.layout.activity_menu2__icerik_);


        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView = (TextView) findViewById(R.id.icerik_http_text);
            pDialog = new ProgressDialog(Menu2_Icerik_Activity.this);
            pDialog.setMessage("Getting Data ...");
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
            pDialog.dismiss();
            try {

                JSONObject jsonObj = new JSONObject(json);
                String icerik = jsonObj.getString(TAG_ICERIK);
                textView.setText(icerik);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
