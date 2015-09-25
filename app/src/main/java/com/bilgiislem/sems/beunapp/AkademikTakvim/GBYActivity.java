package com.bilgiislem.sems.beunapp.AkademikTakvim;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.R;

public class GBYActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView loadingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gby);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        android.support.v4.app.Fragment objFragment = null;
        objFragment = new GBYTab_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, objFragment).commit();

        loadingData = (TextView) findViewById(R.id.loading_data);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("kategori_isim"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

}