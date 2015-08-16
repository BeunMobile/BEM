package com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersembeTabFragment extends Fragment {

    TextView corbaText;
    TextView corbaCalText;
    TextView yemek1Text;
    TextView yemek1CalText;
    TextView yemek2Text;
    TextView yemek2CalText;
    TextView digerText;
    TextView digerCalText;

    private static final String TAG_LISTE = "liste";
    private static final String TAG_YEMEKLER = "yemekler";
    private static final String TAG_YEMEK = "yemek";
    private static final String TAG_KALORI = "kalori";
    private static final String TAG_CINS = "cins";
    private static final String TAG_GUN = "gun";
    private static final String TAG_TARIH = "tarih";

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    final int currentDay = localCalendar.get(Calendar.DATE);
    final int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
    final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    final int currentYear = localCalendar.get(Calendar.YEAR);
    final int dayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);


    String url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear;


    List<String> corbaList = new ArrayList<>();
    List<String> corbaCalList = new ArrayList<>();

    List<String> yemek1List = new ArrayList<>();
    List<String> yemek1CalList = new ArrayList<>();

    List<String> yemek2List = new ArrayList<>();
    List<String> yemek2CalList = new ArrayList<>();

    List<String> digerList = new ArrayList<>();
    List<String> digerCalList = new ArrayList<>();

    List<String> tarihList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.persembe_tab, container, false);

       /* corbaCalText = (TextView) view.findViewById(R.id.corba_cal);
        yemek1Text = (TextView) view.findViewById(R.id.yemek1_text);
        yemek1CalText = (TextView) view.findViewById(R.id.yemek1_cal);
        yemek2Text = (TextView) view.findViewById(R.id.yemek2_text);
        yemek2CalText = (TextView) view.findViewById(R.id.yemek2_cal);
        digerText = (TextView) view.findViewById(R.id.diger_text);
        digerCalText = (TextView) view.findViewById(R.id.diger_cal);*/

        corbaText = (TextView) view.findViewById(R.id.corba_text);


        new JSONParse().execute();

        return view;
    }


    private class JSONParse extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... args0) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray liste = jsonObj.getJSONArray(TAG_LISTE);
                    for (int i = 0; i < liste.length(); i++) {
                        JSONObject listeobject = liste.getJSONObject(i);
                        JSONArray yemekler = listeobject.getJSONArray(TAG_YEMEKLER);
                        String gun = listeobject.getString(TAG_GUN);
                        String tarih = listeobject.getString(TAG_TARIH);
                        String tarihSayac = tarih.substring(tarih.lastIndexOf("-") + 1);
                        tarihList.add(tarihSayac);
                        for (int j = 0; j < yemekler.length(); j++) {
                            JSONObject yemeklerobject = yemekler.getJSONObject(j);
                            String yemek = yemeklerobject.getString(TAG_YEMEK);
                            String kalori = yemeklerobject.getString(TAG_KALORI);
                            String cins = yemeklerobject.getString(TAG_CINS);
                            if (cins.equals("1")) {
                                corbaList.add(yemek);
                                corbaCalList.add(kalori);
                            } else if (cins.equals("2")) {
                                yemek1List.add(yemek);
                                yemek1CalList.add(kalori);
                            } else if (cins.equals("3")) {
                                yemek2List.add(yemek);
                                yemek2CalList.add(kalori);
                            } else if (cins.equals("4")) {
                                digerList.add(yemek);
                                digerCalList.add(kalori);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), R.string.yemek_no_data, Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            corbaText.setText(corbaList.get(3));
        }
    }
}
