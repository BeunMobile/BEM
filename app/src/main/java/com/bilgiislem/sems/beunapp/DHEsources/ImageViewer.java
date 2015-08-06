package com.bilgiislem.sems.beunapp.DHEsources;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by detro on 6.08.2015.
 */
public class ImageViewer extends ListActivity {

    private ProgressDialog pDialog;

    private static String url;

    List<Integer> list = new ArrayList<>();


    private static final String TAG_LISTE = "liste";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";


    JSONArray contacts = null;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dhe_layout_all);
        getActionBar().setTitle(getIntent().getStringExtra("title"));
        url = "http://w3.beun.edu.tr/mobil-arsiv/" + getIntent().getStringExtra("datelink");

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {/*
                String adres2 = contactList.get(position).get("adres");
                String baslik2 = contactList.get(position).get("baslik");
                Intent intent = new Intent(DHE_Month_Year.this, Icerik_Activity.class);
                intent.putExtra("key", adres2);
                intent.putExtra("key2", baslik2);
                startActivity(intent);*/
            }
        });

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ImageViewer.this);
            pDialog.setTitle(ImageViewer.this.getString(R.string.loading));
            pDialog.setMessage(ImageViewer.this.getString(R.string.waitfor));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    contacts = jsonObj.getJSONArray(TAG_LISTE);

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String baslik = c.getString(TAG_BASLIK);
                        String adres = c.getString(TAG_ADRES);
                        HashMap<String, String> contact = new HashMap<String, String>();
                        if (baslik.contains("<b>") || baslik.contains("<strong>")) {
                            list.add(i);
                        }
                        baslik = html2text(baslik);
                        contact.put(TAG_BASLIK, baslik);
                        contact.put(TAG_ADRES, adres);
                        contactList.add(contact);
                    }
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

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (contacts.toString().contains("[]")) {
                Toast.makeText(getApplicationContext(), R.string.dhe_all_no_data, Toast.LENGTH_LONG).show();
                finish();
            }
            ListAdapter adapter = new SimpleAdapter(
                    ImageViewer.this, contactList,
                    R.layout.json_items, new String[]{TAG_BASLIK}, new int[]{R.id.news});
            setListAdapter(adapter);
        }

    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
