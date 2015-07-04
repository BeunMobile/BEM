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


}
