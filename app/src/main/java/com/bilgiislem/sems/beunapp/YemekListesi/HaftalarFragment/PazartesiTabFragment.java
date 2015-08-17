package com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class PazartesiTabFragment extends Fragment {

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
    final int dayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
    final int weekOfMonth = localCalendar.get(Calendar.WEEK_OF_MONTH);
    final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    final int currentYear = localCalendar.get(Calendar.YEAR);


    String url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear;
    int whichWeek;
    int firstdayOfMonth;

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

        View view = inflater.inflate(R.layout.pazartesi_tab, container, false);

        corbaText = (TextView) view.findViewById(R.id.corba_text);
        corbaCalText = (TextView) view.findViewById(R.id.corba_cal);
        yemek1Text = (TextView) view.findViewById(R.id.yemek1_text);
        yemek1CalText = (TextView) view.findViewById(R.id.yemek1_cal);
        yemek2Text = (TextView) view.findViewById(R.id.yemek2_text);
        yemek2CalText = (TextView) view.findViewById(R.id.yemek2_cal);
        digerText = (TextView) view.findViewById(R.id.diger_text);
        digerCalText = (TextView) view.findViewById(R.id.diger_cal);

        corbaText.setVisibility(View.GONE);
        corbaCalText.setVisibility(View.GONE);
        yemek1Text.setVisibility(View.GONE);
        yemek1CalText.setVisibility(View.GONE);
        yemek2Text.setVisibility(View.GONE);
        yemek2CalText.setVisibility(View.GONE);
        digerText.setVisibility(View.GONE);
        digerCalText.setVisibility(View.GONE);

        if (dayOfWeek != 0 && dayOfWeek != 7) {
            new JSONParse().execute();
        } else {
            Toast toast = Toast.makeText(getActivity(), R.string.closed_restaurant, Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if (v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        return view;
    }


    private class JSONParse extends AsyncTask<Void, Void, Void> {
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
            //Log.d("WOM", "" + weekOfMonth);

            try {
                //Log.d("GNOFS", " " + getNumberofSunday(currentYear + "-" + currentMonth + 1 + "-" + 1, currentYear + "-" + currentMonth + 1 + "-" + 1));
                whichWeek = getNumberofSunday(currentYear + "-" + currentMonth + "-" + 1, currentYear + "-" + currentMonth + "-" + currentDay);
                firstdayOfMonth = getFirstDateOfCurrentMonth();
                if (firstdayOfMonth != 1 && firstdayOfMonth != 7) {
                    switch (whichWeek) {
                        case 1:
                            corbaText.setText(corbaList.get(0));
                            corbaCalText.setText(corbaCalList.get(0) + " cal");
                            yemek1Text.setText(yemek1List.get(0));
                            yemek1CalText.setText(yemek1CalList.get(0) + " cal");
                            yemek2Text.setText(yemek2List.get(0));
                            yemek2CalText.setText(yemek2CalList.get(0) + " cal");
                            digerText.setText(digerList.get(0));
                            digerCalText.setText(digerCalList.get(0) + " cal");
                            break;
                        case 2:
                            corbaText.setText(corbaList.get(5));
                            corbaCalText.setText(corbaCalList.get(5) + " cal");
                            yemek1Text.setText(yemek1List.get(5));
                            yemek1CalText.setText(yemek1CalList.get(5) + " cal");
                            yemek2Text.setText(yemek2List.get(5));
                            yemek2CalText.setText(yemek2CalList.get(5) + " cal");
                            digerText.setText(digerList.get(5));
                            digerCalText.setText(digerCalList.get(5) + " cal");
                            break;
                        case 3:
                            corbaText.setText(corbaList.get(10));
                            corbaCalText.setText(corbaCalList.get(10) + " cal");
                            yemek1Text.setText(yemek1List.get(10));
                            yemek1CalText.setText(yemek1CalList.get(10) + " cal");
                            yemek2Text.setText(yemek2List.get(10));
                            yemek2CalText.setText(yemek2CalList.get(10) + " cal");
                            digerText.setText(digerList.get(10));
                            digerCalText.setText(digerCalList.get(10) + " cal");
                            break;
                        case 4:
                            corbaText.setText(corbaList.get(15));
                            corbaCalText.setText(corbaCalList.get(15) + " cal");
                            yemek1Text.setText(yemek1List.get(15));
                            yemek1CalText.setText(yemek1CalList.get(15) + " cal");
                            yemek2Text.setText(yemek2List.get(15));
                            yemek2CalText.setText(yemek2CalList.get(15) + " cal");
                            digerText.setText(digerList.get(15));
                            digerCalText.setText(digerCalList.get(15) + " cal");
                            break;
                        case 5:
                            corbaText.setText(corbaList.get(20));
                            corbaCalText.setText(corbaCalList.get(20) + " cal");
                            yemek1Text.setText(yemek1List.get(20));
                            yemek1CalText.setText(yemek1CalList.get(20) + " cal");
                            yemek2Text.setText(yemek2List.get(20));
                            yemek2CalText.setText(yemek2CalList.get(20) + " cal");
                            digerText.setText(digerList.get(20));
                            digerCalText.setText(digerCalList.get(20) + " cal");
                            break;
                        default:
                            Toast.makeText(getActivity(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    switch (whichWeek - 1) {
                        case 0:
                            corbaText.setText(corbaList.get(0));
                            corbaCalText.setText(corbaCalList.get(0) + " cal");
                            yemek1Text.setText(yemek1List.get(0));
                            yemek1CalText.setText(yemek1CalList.get(0) + " cal");
                            yemek2Text.setText(yemek2List.get(0));
                            yemek2CalText.setText(yemek2CalList.get(0) + " cal");
                            digerText.setText(digerList.get(0));
                            digerCalText.setText(digerCalList.get(0) + " cal");
                            break;
                        case 1:
                            corbaText.setText(corbaList.get(5));
                            corbaCalText.setText(corbaCalList.get(5) + " cal");
                            yemek1Text.setText(yemek1List.get(5));
                            yemek1CalText.setText(yemek1CalList.get(5) + " cal");
                            yemek2Text.setText(yemek2List.get(5));
                            yemek2CalText.setText(yemek2CalList.get(5) + " cal");
                            digerText.setText(digerList.get(5));
                            digerCalText.setText(digerCalList.get(5) + " cal");
                            break;
                        case 2:
                            corbaText.setText(corbaList.get(10));
                            corbaCalText.setText(corbaCalList.get(10) + " cal");
                            yemek1Text.setText(yemek1List.get(10));
                            yemek1CalText.setText(yemek1CalList.get(10) + " cal");
                            yemek2Text.setText(yemek2List.get(10));
                            yemek2CalText.setText(yemek2CalList.get(10) + " cal");
                            digerText.setText(digerList.get(10));
                            digerCalText.setText(digerCalList.get(10) + " cal");
                            break;
                        case 3:
                            corbaText.setText(corbaList.get(15));
                            corbaCalText.setText(corbaCalList.get(15) + " cal");
                            yemek1Text.setText(yemek1List.get(15));
                            yemek1CalText.setText(yemek1CalList.get(15) + " cal");
                            yemek2Text.setText(yemek2List.get(15));
                            yemek2CalText.setText(yemek2CalList.get(15) + " cal");
                            digerText.setText(digerList.get(15));
                            digerCalText.setText(digerCalList.get(15) + " cal");
                            break;
                        case 4:
                            corbaText.setText(corbaList.get(20));
                            corbaCalText.setText(corbaCalList.get(20) + " cal");
                            yemek1Text.setText(yemek1List.get(20));
                            yemek1CalText.setText(yemek1CalList.get(20) + " cal");
                            yemek2Text.setText(yemek2List.get(20));
                            yemek2CalText.setText(yemek2CalList.get(20) + " cal");
                            digerText.setText(digerList.get(20));
                            digerCalText.setText(digerCalList.get(20) + " cal");
                            break;
                        default:
                            Toast.makeText(getActivity(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            corbaText.setVisibility(View.VISIBLE);
            corbaCalText.setVisibility(View.VISIBLE);
            yemek1Text.setVisibility(View.VISIBLE);
            yemek1CalText.setVisibility(View.VISIBLE);
            yemek2Text.setVisibility(View.VISIBLE);
            yemek2CalText.setVisibility(View.VISIBLE);
            digerText.setVisibility(View.VISIBLE);
            digerCalText.setVisibility(View.VISIBLE);
        }
    }

    public int getNumberofSunday(String d1, String d2) throws Exception { // object
        Date date1 = getDate(d1);
        Date date2 = getDate(d2);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        int mondays = 0;
        while (c2.after(c1)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                mondays++;
            }
            c1.add(Calendar.DATE, 1);
        }

        return mondays;
    }

    // converts string to date
    public Date getDate(String s) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    private int getFirstDateOfCurrentMonth() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }
}