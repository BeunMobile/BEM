package com.bilgiislem.sems.beunapp.DHE_Sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.MainAndWeb.MainActivity;
import com.bilgiislem.sems.beunapp.R;

public class MYActivity extends ListActivity implements AppCompatCallback {

    private ProgressDialog pDialog;

    private static String url;

    private AppCompatDelegate delegate;

    List<Integer> list = new ArrayList<>();

    private static final String TAG_LISTE = "liste";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";

    JSONArray contacts = null;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = "http://w3.beun.edu.tr/mobil-arsiv/" + getIntent().getStringExtra("datelink");

        Log.d("Test", "" + getIntent().getStringExtra("datelink"));
        contactList = new ArrayList<HashMap<String, String>>();

        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_dheall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        delegate.setSupportActionBar(toolbar);
        delegate.setTitle(getIntent().getStringExtra("title"));

        delegate.getSupportActionBar().setHomeButtonEnabled(true);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String adres2 = contactList.get(position).get("adres");
                String baslik2 = contactList.get(position).get("baslik");
                Intent intent = new Intent(MYActivity.this, IcerikActivity.class);
                intent.putExtra("adres", adres2);
                intent.putExtra("baslik", baslik2);
                startActivity(intent);
            }
        });

        new GetContacts().execute();
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MYActivity.this);
            pDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
            pDialog.setTitle(MYActivity.this.getString(R.string.loading));
            pDialog.setMessage(MYActivity.this.getString(R.string.waitfor));
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
                Toast.makeText(getApplicationContext(), R.string.duyuru_no_data, Toast.LENGTH_SHORT).show();
                finish();
            }
            ListAdapter adapter = new SimpleAdapter(
                    MYActivity.this, contactList,
                    R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
            setListAdapter(adapter);
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