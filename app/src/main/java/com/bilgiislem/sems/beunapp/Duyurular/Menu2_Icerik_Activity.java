package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu2_Icerik_Activity extends ActionBarActivity {

    private ProgressDialog pDialog;
    String baslik_plus, http_plus;
    TextView textView;
    private static String url;
    private static final String TAG_ICERIK = "icerik";
    JSONArray contacts = null;


    ArrayList<HashMap<String, String>> contactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baslik_plus = getIntent().getStringExtra("key2");
        http_plus = getIntent().getStringExtra("key");
        url = "http://w3.beun.edu.tr/veri" + http_plus;


        getSupportActionBar().setTitle(baslik_plus);
        setContentView(R.layout.activity_menu2__icerik_);


        textView = (TextView) findViewById(R.id.icerik_http_text);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        contactList = new ArrayList<HashMap<String, String>>();

        // Calling async task to get json
        new GetContacts().execute();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Menu2_Icerik_Activity.this);
            pDialog.setMessage(Menu2_Icerik_Activity.this.getString(R.string.waitfor));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_ICERIK);

                    String icerik = jsonObj.getString(TAG_ICERIK);

                    HashMap<String, String> contact = new HashMap<String, String>();

                    icerik = html2text(icerik);

                    contact.put(TAG_ICERIK, icerik);
                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ListAdapter adapter = new SimpleAdapter(
                    Menu2_Icerik_Activity.this, contactList,
                    R.layout.json_items, new String[]{TAG_ICERIK}, new int[]{R.id.name});

            setListAdapter(adapter);
        }


    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
