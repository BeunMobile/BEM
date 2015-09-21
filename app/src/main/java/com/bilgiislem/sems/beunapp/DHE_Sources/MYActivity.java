package com.bilgiislem.sems.beunapp.DHE_Sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class MYActivity extends ListActivity implements AppCompatCallback {


    private static String url;

    private AppCompatDelegate delegate;

    List<Integer> list = new ArrayList<>();

    private static final String TAG_LISTE = "liste";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";

    JSONArray JSONData = null;

    TextView loadingData;
    ListView listView;

    ArrayList<HashMap<String, String>> JSONDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = "http://w3.beun.edu.tr/mobil-arsiv/" + getIntent().getStringExtra("datelink");
        JSONDataList = new ArrayList<HashMap<String, String>>();
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_dheall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        delegate.setSupportActionBar(toolbar);
        delegate.setTitle(getIntent().getStringExtra("title"));
        delegate.getSupportActionBar().setHomeButtonEnabled(true);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingData = (TextView) findViewById(R.id.loading_data);
        listView = getListView();
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String adres2 = JSONDataList.get(position).get("adres");
                String baslik2 = JSONDataList.get(position).get("baslik");
                Intent intent = new Intent(MYActivity.this, IcerikActivity.class);
                intent.putExtra("adres", adres2);
                intent.putExtra("baslik", baslik2);
                startActivity(intent);
            }
        });

        new GetJSON().execute();
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

    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONData = jsonObj.getJSONArray(TAG_LISTE);
                    for (int i = 0; i < JSONData.length(); i++) {
                        JSONObject c = JSONData.getJSONObject(i);
                        String baslik = c.getString(TAG_BASLIK);
                        String adres = c.getString(TAG_ADRES);
                        HashMap<String, String> contact = new HashMap<String, String>();
                        if (baslik.contains("<b>") || baslik.contains("<strong>")) {
                            list.add(i);
                        }
                        baslik = html2text(baslik);
                        contact.put(TAG_BASLIK, baslik);
                        contact.put(TAG_ADRES, adres);
                        JSONDataList.add(contact);
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
            if (JSONData.toString().contains("[]")) {
                Toast.makeText(getApplicationContext(), R.string.duyuru_no_data, Toast.LENGTH_SHORT).show();
                finish();
            }
            try {
                ListAdapter adapter = new SimpleAdapter(
                        MYActivity.this, JSONDataList,
                        R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
                setListAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                Log.d("NullPointer", "In this try.");
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