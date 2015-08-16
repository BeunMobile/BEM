package com.bilgiislem.sems.beunapp.YemekListesi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;


import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.CarsambaTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.CumaTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.PazartesiTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.PersembeTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.SaliTabFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class TabFragment extends Fragment {

    //OnYemekSetListener onYemekSetListener;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Button buttonPdf;
    public static int int_items = 5;

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


        View view = inflater.inflate(R.layout.menu5_tab_layout, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        final String cM = "" + currentMonth;
        final String cY = "" + currentYear;


        buttonPdf = (Button) view.findViewById(R.id.button_pdf);
        buttonPdf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YemekPDF.class);
                intent.putExtra("month", cM);
                intent.putExtra("year", cY);
                startActivity(intent);
            }
        });


       /* if (dayOfWeek != 0 && dayOfWeek != 7) {

            new JSONParse().execute();

        }*/

        /**
         *Set an Apater for the View Pager
         */

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager.getAdapter().notifyDataSetChanged();

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PazartesiTabFragment();
                case 1:
                    return new SaliTabFragment();
                case 2:
                    return new CarsambaTabFragment();
                case 3:
                    return new PersembeTabFragment();
                case 4:
                    return new CumaTabFragment();
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_pazartesi);
                case 1:
                    return getResources().getString(R.string.tab_sali);
                case 2:
                    return getResources().getString(R.string.tab_carsamba);
                case 3:
                    return getResources().getString(R.string.tab_persembe);
                case 4:
                    return getResources().getString(R.string.tab_cuma);
            }
            return null;
        }
    }


}

/*


    private class JSONParse extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setTitle(getString(R.string.loading));
            pDialog.setMessage(getString(R.string.waitfor));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }












 public interface OnYemekSetListener {
        public void setCorba(List corba);
    }

        @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onYemekSetListener = (OnYemekSetListener) activity;
        } catch (Exception e) {
        }

    }*/