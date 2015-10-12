package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.DHE_Sources.IcerikActivity;
import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.MainActivity.MainActivity;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class AnaSayfa_Fragment extends Fragment {


    private static String url_duyuru = "http://w3.beun.edu.tr/mobil-duyurular/";
    private static String url_haber = "http://w3.beun.edu.tr/mobil-haberler/";
    private static String url_etkinlik = "http://w3.beun.edu.tr/mobil-etkinlikler/";
    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    JSONArray s1 = null;

    TextView emptyDuyuru, setDuyuru, loadDuyuru, emptyHaber, setHaber, loadHaber, emptyEtkinlik, setEtkinlik, loadEtkinlik;
    CardView cardDuyuru, cardHaber, cardEtkinlik;

    String baslikDuyuru, adresDuyuru, baslikHaber, adresHaber, baslikEtkinlik, adresEtkinlik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.ana_sayfa_title));

        cardDuyuru = (CardView) view.findViewById(R.id.card_duyuru);
        cardHaber = (CardView) view.findViewById(R.id.card_haber);
        cardEtkinlik = (CardView) view.findViewById(R.id.card_etkinlik);

        emptyDuyuru = (TextView) view.findViewById(R.id.card_no_duyuru);
        setDuyuru = (TextView) view.findViewById(R.id.card_duyuru_icerik);
        loadDuyuru = (TextView) view.findViewById(R.id.card_duyuru_loading);
        emptyHaber = (TextView) view.findViewById(R.id.card_no_haber);
        setHaber = (TextView) view.findViewById(R.id.card_haber_icerik);
        loadHaber = (TextView) view.findViewById(R.id.card_haber_loading);
        emptyEtkinlik = (TextView) view.findViewById(R.id.card_no_etkinlik);
        setEtkinlik = (TextView) view.findViewById(R.id.card_etkinlik_icerik);
        loadEtkinlik = (TextView) view.findViewById(R.id.card_etkinlik_loading);

        final Button sendFreeTextButton = (Button) view.findViewById(R.id.ana_sayfa_button);
        sendFreeTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("web", "http://w3.beun.edu.tr/");
                AnaSayfa_Fragment.this.startActivity(intent);
            }
        });

        new getDuyuruJSON().execute();
        new getHaberJSON().execute();
        new getEtkinlikJSON().execute();

        return view;
    }

    private class getDuyuruJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_duyuru, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikDuyuru = c.getString(TAG_BASLIK);
                        adresDuyuru = c.getString(TAG_ADRES);
                        baslikDuyuru = html2text(baslikDuyuru);
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
        protected void onPostExecute(Void aVoid) {
            loadDuyuru.setVisibility(View.GONE);
            try {
                if (!baslikDuyuru.isEmpty()) {
                    setDuyuru.setText(baslikDuyuru);
                    cardDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                    setDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyDuyuru.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyDuyuru.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }

    private class getHaberJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_haber, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikHaber = c.getString(TAG_BASLIK);
                        adresHaber = c.getString(TAG_ADRES);
                        baslikHaber = html2text(baslikHaber);
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
        protected void onPostExecute(Void aVoid) {
            loadHaber.setVisibility(View.GONE);
            try {
                if (!baslikHaber.isEmpty()) {
                    setHaber.setText(baslikHaber);
                    cardHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                    setHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyHaber.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyHaber.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }


    private class getEtkinlikJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_etkinlik, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikEtkinlik = c.getString(TAG_BASLIK);
                        adresEtkinlik = c.getString(TAG_ADRES);
                        baslikEtkinlik = html2text(baslikEtkinlik);
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
        protected void onPostExecute(Void aVoid) {
            loadEtkinlik.setVisibility(View.GONE);
            try {
                if (!baslikEtkinlik.isEmpty()) {
                    setEtkinlik.setText(baslikEtkinlik);
                    cardEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                    setEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyEtkinlik.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyEtkinlik.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


}