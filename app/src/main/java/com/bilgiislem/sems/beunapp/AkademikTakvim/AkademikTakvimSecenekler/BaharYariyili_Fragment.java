package com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimSecenekler;

/**
 * Created by detro on 24.09.2015.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimAdapter.FeedItem;
import com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimAdapter.MyRecyclerAdapter;
import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaharYariyili_Fragment extends Fragment {

    //private static final String TAG_DONEM = "donem";
    private static final String TAG_KATEGORIID = "kategori_id";
    //private static final String TAG_TAKVIM = "takvim";
    //private static final String TAG_BASLIK = "baslik";
    //private static final String TAG_BASLANGIC = "baslangic";
    //private static final String TAG_BITIS = "bitis";
    //private static final String TAG_GUN = "gun";
    //private static final String TAG_AY = "ay";
    //private static final String TAG_YIL = "yil";
    //private static final String TAG_RENK = "renk";

    //JSONArray AllJSONTakvimData = null, JSONTakvimSelectedData = null;
    //JSONObject JSONBaslangicData = null, JSONBitisData = null;
    RecyclerView recyclerView;
    private List<FeedItem> feedsList;
    private MyRecyclerAdapter adapter;
    TextView loadingData, emptyData;
    //String urlJSONData = "http://w3.beun.edu.tr/akademik_takvim/veri/?yil=2015";
    String urlJsoupData = "http://w3.beun.edu.tr/akademik_takvim/?yil=2015";
    //String donem = "3";

    public BaharYariyili_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gby_recyc, container, false);
        loadingData = (TextView) view.findViewById(R.id.loading_data);
        emptyData = (TextView) view.findViewById(R.id.empty_data);
        emptyData.setText(R.string.empty_bahar);
        emptyData.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //new getBaharJSON().execute();
        new getBaharJsoup().execute();
        return view;
    }

    private class getBaharJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String kategori_id = getActivity().getIntent().getStringExtra(TAG_KATEGORIID);
                Document doc = Jsoup.connect(urlJsoupData).get();
                feedsList = new ArrayList<>();
                try {
                    if (doc.toString() != null) {
                        for (Element tbody : doc.select("div#" + "k-" + kategori_id + " > div.tab-content > div#d-1-" + kategori_id + " > table > tbody > tr")) {
                            FeedItem item = new FeedItem();

                            String[] c2ss = new String[2];
                            try {
                                int i = 0;
                                for (Element color : tbody.select("td")) {
                                    String c2s = color.select("div").attr("style");
                                    c2ss[i] = c2s.substring(c2s.indexOf("#") + 1, c2s.indexOf("#") + 7);
                                    i++;
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }

                            String d2s = tbody.select("td div span span").text();
                            String[] d2ss = d2s.split("\\s+");
                            for (int j = 0; j < d2ss.length; j++) {
                                if (d2ss.length > 0) {
                                    item.setFirstDay(d2ss[0]);
                                    item.setFirstMonth(d2ss[1]);
                                    item.setFirstYear(d2ss[2]);
                                    item.setFirstDateColor(c2ss[0]);
                                } else {
                                    item.setFirstDay("");
                                    item.setFirstMonth("");
                                    item.setFirstYear("");
                                    item.setFirstDateColor("00ffffff");
                                }
                                if (d2ss.length > 3) {
                                    item.setSecDay(d2ss[3]);
                                    item.setSecMonth(d2ss[4]);
                                    item.setSecYear(d2ss[5]);
                                    item.setSecDateColor(c2ss[1]);
                                } else {
                                    item.setSecDay("");
                                    item.setSecMonth("");
                                    item.setSecYear("");
                                    item.setSecDateColor("00ffffff");
                                }
                            }
                            item.setTitle(tbody.select("td a").text());
                            feedsList.add(item);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new MyRecyclerAdapter(getActivity(), feedsList);
            recyclerView.setAdapter(adapter);
            loadingData.setVisibility(View.GONE);
            try {
                if (feedsList.isEmpty()) {
                    emptyData.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                Log.d("NullPointer", "Null object reference.");
            }
        }
    }


    /*private class getBaharJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String AllJSONStr = sh.makeServiceCall(urlJSONData, ServiceHandler.GET);
            try {
                String kategori_id = getActivity().getIntent().getStringExtra(TAG_KATEGORIID);
                if (AllJSONStr != null) {
                    try {
                        JSONObject AllJSONObj = new JSONObject(AllJSONStr);
                        AllJSONTakvimData = AllJSONObj.getJSONArray(TAG_TAKVIM);
                        feedsList = new ArrayList<>();
                        for (int i = 0; i < AllJSONTakvimData.length(); i++) {
                            JSONObject AllJSONTakvimObj = AllJSONTakvimData.getJSONObject(i);
                            String AllKategoriIDStr = AllJSONTakvimObj.getString(TAG_KATEGORIID);
                            if (AllKategoriIDStr.equals(kategori_id)) {
                                JSONTakvimSelectedData = AllJSONTakvimObj.getJSONArray(TAG_TAKVIM);
                                for (int j = 0; j < JSONTakvimSelectedData.length(); j++) {
                                    JSONObject JSONTakvimObj = JSONTakvimSelectedData.getJSONObject(j);
                                    String donemStr = JSONTakvimObj.getString(TAG_DONEM);
                                    if (donemStr.equals(donem)) {
                                        String baslikStr = JSONTakvimObj.getString(TAG_BASLIK);
                                        FeedItem item = new FeedItem();
                                        if (!JSONTakvimObj.getString(TAG_BASLANGIC).equals("")) {
                                            JSONBaslangicData = JSONTakvimObj.getJSONObject(TAG_BASLANGIC);
                                            String firstDay = JSONBaslangicData.getString(TAG_GUN);
                                            String firstMonth = JSONBaslangicData.getString(TAG_AY);
                                            String firstYear = JSONBaslangicData.getString(TAG_YIL);
                                            String firstColor = JSONBaslangicData.getString(TAG_RENK);
                                            item.setFirstDay(firstDay);
                                            item.setFirstMonth(firstMonth);
                                            item.setFirstYear(firstYear);
                                            item.setFirstDateColor(firstColor);
                                        } else {
                                            String firstDay = "";
                                            String firstMonth = "";
                                            String firstYear = "";
                                            String firstColor = "00ffffff";
                                            item.setFirstDay(firstDay);
                                            item.setFirstMonth(firstMonth);
                                            item.setFirstYear(firstYear);
                                            item.setFirstDateColor(firstColor);
                                        }
                                        if (!JSONTakvimObj.getString(TAG_BITIS).equals("")) {
                                            JSONBitisData = JSONTakvimObj.getJSONObject(TAG_BITIS);
                                            String secDay = JSONBitisData.getString(TAG_GUN);
                                            String secMonth = JSONBitisData.getString(TAG_AY);
                                            String secYear = JSONBitisData.getString(TAG_YIL);
                                            String secColor = JSONBitisData.getString(TAG_RENK);
                                            item.setSecDay(secDay);
                                            item.setSecMonth(secMonth);
                                            item.setSecYear(secYear);
                                            item.setSecDateColor(secColor);
                                        } else {
                                            String secDay = "";
                                            String secMonth = "";
                                            String secYear = "";
                                            String secColor = "00ffffff";
                                            item.setSecDay(secDay);
                                            item.setSecMonth(secMonth);
                                            item.setSecYear(secYear);
                                            item.setSecDateColor(secColor);
                                        }
                                        item.setTitle(baslikStr);
                                        feedsList.add(item);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            } catch (NullPointerException e) {
                Log.d("kategori_id", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new MyRecyclerAdapter(getActivity(), feedsList);
            recyclerView.setAdapter(adapter);
            loadingData.setVisibility(View.GONE);
            try {
                if (feedsList.isEmpty()) {
                    emptyData.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                Log.d("NullPointer", "Null object reference.");
            }
        }
    }*/
}