package com.bilgiislem.sems.beunapp.YemekListesi.YemekListesiHaftalar;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class Cuma_Fragment extends Fragment {

    TextView dateText;
    TextView noconText;
    TextView weekendText;
    TextView corbaText;
    TextView corbaCalText;
    TextView yemek1Text;
    TextView yemek1CalText;
    TextView yemek2Text;
    TextView yemek2CalText;
    TextView digerText;
    TextView digerCalText;

    /*private static final String TAG_LISTE = "liste";
    private static final String TAG_YEMEKLER = "yemekler";
    private static final String TAG_YEMEK_ISIM = "isim";
    private static final String TAG_KALORI = "kalori";
    private static final String TAG_CINS = "cins";
    private static final String TAG_GUN = "gun";
    private static final String TAG_TARIH = "tarih";*/

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    final int dayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
    //final int weekOfMonth = localCalendar.get(Calendar.WEEK_OF_MONTH);
    final int currentDay = localCalendar.get(Calendar.DATE);
    final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    final int currentYear = localCalendar.get(Calendar.YEAR);

    int whichWeek;
    int firstdayOfMonth;

    /*List<String> corbaList = new ArrayList<>();
    List<String> corbaCalList = new ArrayList<>();

    List<String> yemek1List = new ArrayList<>();
    List<String> yemek1CalList = new ArrayList<>();

    List<String> yemek2List = new ArrayList<>();
    List<String> yemek2CalList = new ArrayList<>();

    List<String> digerList = new ArrayList<>();
    List<String> digerCalList = new ArrayList<>();

    List<String> tarihList = new ArrayList<>();*/
    List<String> aylarTRList = Arrays.asList("Ocak", "Þubat", "Mart", "Nisan", "Mayýs",
            "Haziran", "Temmuz", "Aðustos", "Eylül", "Ekim", "Kasým", "Aralýk");

    public String url, currentDate, currentDayText;
    public String corbaYemek, corbaCalYemek, yemek1Yemek, yemek1CalYemek, yemek2Yemek, yemek2CalYemek, digerYemek, digerCalYemek;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_days, container, false);

        dateText = (TextView) view.findViewById(R.id.date_text);
        noconText = (TextView) view.findViewById(R.id.no_data);
        weekendText = (TextView) view.findViewById(R.id.weekend_text);
        corbaText = (TextView) view.findViewById(R.id.corba_text);
        corbaCalText = (TextView) view.findViewById(R.id.corba_cal);
        yemek1Text = (TextView) view.findViewById(R.id.yemek1_text);
        yemek1CalText = (TextView) view.findViewById(R.id.yemek1_cal);
        yemek2Text = (TextView) view.findViewById(R.id.yemek2_text);
        yemek2CalText = (TextView) view.findViewById(R.id.yemek2_cal);
        digerText = (TextView) view.findViewById(R.id.diger_text);
        digerCalText = (TextView) view.findViewById(R.id.diger_cal);


        corbaText.setVisibility(View.INVISIBLE);
        corbaCalText.setVisibility(View.INVISIBLE);
        yemek1Text.setVisibility(View.INVISIBLE);
        yemek1CalText.setVisibility(View.INVISIBLE);
        yemek2Text.setVisibility(View.INVISIBLE);
        yemek2CalText.setVisibility(View.INVISIBLE);
        digerText.setVisibility(View.INVISIBLE);
        digerCalText.setVisibility(View.INVISIBLE);


        //cuma==6
        if (dayOfWeek == 6) {
            //url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + currentDay;
            currentDate = " " + currentDay + "." + "0" + currentMonth + "." + currentYear;
            currentDayText = currentDay + "";
        } else if (dayOfWeek == 2) {
            //url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + (currentDay - 1);
            currentDate = " " + (currentDay + 4) + "." + "0" + currentMonth + "." + currentYear;
            currentDayText = (currentDay + 4) + "";
        } else if (dayOfWeek == 3) {
            //url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + (currentDay - 2);
            currentDate = " " + (currentDay + 3) + "." + "0" + currentMonth + "." + currentYear;
            currentDayText = (currentDay + 3) + "";
        } else if (dayOfWeek == 4) {
            //url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + (currentDay - 3);
            currentDate = " " + (currentDay + 2) + "." + "0" + currentMonth + "." + currentYear;
            currentDayText = (currentDay + 2) + "";
        } else if (dayOfWeek == 5) {
            //url = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + (currentDay - 4);
            currentDate = " " + (currentDay + 1) + "." + "0" + currentMonth + "." + currentYear;
            currentDayText = (currentDay + 1) + "";
        }


        if (!isOnline()) {
            dateText.setVisibility(View.GONE);
            noconText.setVisibility(View.VISIBLE);
        } else {
            if (dayOfWeek != 1 && dayOfWeek != 7) {
                //new JSONParse().execute();
                new getFoodString().execute();
            } else {
                dateText.setVisibility(View.GONE);
                weekendText.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    private class getFoodString extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String[] mFood = getResources().getString(R.string.monthly_food).split("\\n");
                for (int i = 0; i < mFood.length; i++) {
                    if (mFood[i].equals(currentDate)) {
                        corbaYemek = mFood[i + 2];
                        corbaCalYemek = mFood[i + 3];
                        yemek1Yemek = mFood[i + 4];
                        yemek1CalYemek = mFood[i + 5];
                        yemek2Yemek = mFood[i + 6];
                        yemek2CalYemek = mFood[i + 7];
                        digerYemek = mFood[i + 8];
                        digerCalYemek = mFood[i + 9];
                    }
                }
            } catch (IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                whichWeek = getNumberofSunday(currentYear + "-" + currentMonth + "-" + 1, currentYear + "-" + currentMonth + "-" + currentDay);
                firstdayOfMonth = getFirstDateOfCurrentMonth();
                dateText.setText(currentDayText + " " + aylarTRList.get(currentMonth - 1) + " " + currentYear);
                corbaText.setText(corbaYemek);
                corbaCalText.setText(corbaCalYemek + " kal");
                yemek1Text.setText(yemek1Yemek);
                yemek1CalText.setText(yemek1CalYemek + " kal");
                yemek2Text.setText(yemek2Yemek);
                yemek2CalText.setText(yemek2CalYemek + " kal");
                digerText.setText(digerYemek);
                digerCalText.setText(digerCalYemek + " kal");

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

   /* private class JSONParse extends AsyncTask<Void, Void, Void> {
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
                            String yemek = yemeklerobject.getString(TAG_YEMEK_ISIM);
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
                Log.d("jsonStrNull", "jsonStr variable is null.");//DEGÝSTÝ
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                whichWeek = getNumberofSunday(currentYear + "-" + currentMonth + "-" + 1, currentYear + "-" + currentMonth + "-" + currentDay);
                firstdayOfMonth = getFirstDateOfCurrentMonth();

                dateText.setText(tarihList.get(0) + " " + aylarTRList.get(currentMonth - 1) + " " + currentYear);
                corbaText.setText(corbaList.get(0));
                corbaCalText.setText(corbaCalList.get(0) + " kal");
                yemek1Text.setText(yemek1List.get(0));
                yemek1CalText.setText(yemek1CalList.get(0) + " kal");
                yemek2Text.setText(yemek2List.get(0));
                yemek2CalText.setText(yemek2CalList.get(0) + " kal");
                digerText.setText(digerList.get(0));
                digerCalText.setText(digerCalList.get(0) + " kal");

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
    }*/

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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}