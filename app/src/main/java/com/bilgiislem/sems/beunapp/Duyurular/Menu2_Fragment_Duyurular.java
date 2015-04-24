package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bilgiislem.sems.beunapp.R;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Menu2_Fragment_Duyurular extends Fragment {
    View rootview;
    Document doc;
    WebView webView;
    String url_duyurular = "http://w3.beun.edu.tr/";
    ProgressDialog mProgressDialog;
    String data = "";
    Element div;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);

        new ListRefresher().execute();
        webView.loadData(data, "text/html", null);
        return rootview;
    }

    private void LoadData(){
        WebView webView = (WebView) rootview.findViewById(R.id.duyurular_page);
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            doc = Jsoup.connect(url_duyurular).get();
            Element div = doc.select("#yazilar").get(0);
            data += div;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected Dialog onCreateDialog(int id) {
        if(id == LOADING_DIALOG){
            ProgressDialog loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage("Loading records ...");
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCancelable(true);
            return loadingDialog;
        }
        return super.onCreateDialog(id);
    }
    private class ListRefresher extends AsyncTask {

        /**
         * This is executed in the UI thread. The only place where we can show the dialog.
         */
        @Override
        protected void onPreExecute() {
            showDialog(LOADING_DIALOG);
        }

        /**
         * This is executed in the background thread.
         */
        @Override
        protected Void doInBackground(Uri... params) {
            LoadData();
            return null;
        }

        /**
         * This is executed in the UI thread. The only place where we can show the dialog.
         */
        @Override
        protected void onPostExecute(Void unused) {
            dismissDialog(LOADING_DIALOG);
        }
    }

}
}